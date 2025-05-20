package PA_Project

import PA_Project.annotations.*
import java.io.BufferedWriter
import java.net.ServerSocket
import java.net.URLDecoder
import java.util.concurrent.Executors
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible

/**
 * Framework minimalista para expor controladores Kotlin via HTTP GET,
 * serializando automaticamente os resultados em JSON.
 *
 * Os controladores são anotados com `@Mapping`, `@Path` e `@Param`.
 * Esta classe trata da associação entre URLs e métodos,
 * análise de parâmetros, execução por reflexão e resposta JSON.
 *
 * Exemplo de utilização:
 * ```
 * val app = GetJson(MyController::class)
 * app.start(8080)
 * ```
 *
 * @constructor Recebe uma ou mais classes de controladores anotados
 */
class GetJson(vararg controllers: KClass<*>) {

    private val routes = mutableMapOf<String, Handler>()

    init {
        for (controllerClass in controllers) {
            val controllerMapping = controllerClass.findAnnotation<Mapping>()?.value ?: ""
            val controllerInstance = controllerClass.createInstance()

            for (method in controllerClass.declaredMemberFunctions) {
                val methodMapping = method.findAnnotation<Mapping>()?.value ?: continue

                val fullPath = normalizePath(controllerMapping, methodMapping)
                method.isAccessible = true

                routes[fullPath] = Handler(controllerInstance, method)
            }
        }
    }

    /**
     * Junta os caminhos da classe e do método numa rota única.
     */
    private fun normalizePath(base: String, sub: String): String {
        return "/" + listOf(base, sub).joinToString("/") { it.trim('/') }
    }

    /**
     * Imprime no terminal todas as rotas registadas, para debug.
     */
    fun printRoutes() {
        for ((path, handler) in routes) {
            println("Route: $path -> ${handler.method.name}")
        }
    }

    /**
     * Inicia o servidor HTTP na porta indicada, escutando pedidos GET.
     * As rotas são processadas conforme as anotações nos controladores registados.
     *
     * @param port Porta TCP onde o servidor deve correr (ex: 8080)
     */
    fun start(port: Int) {
        val server = ServerSocket(port)
        val threadPool = Executors.newFixedThreadPool(10)

        println("🚀 GetJson running on http://localhost:$port")

        while (true) {
            val client = server.accept()
            threadPool.submit {
                client.use {
                    val input = it.getInputStream().bufferedReader()
                    val output = it.getOutputStream().bufferedWriter()

                    val requestLine = input.readLine() ?: return@submit
                    val tokens = requestLine.split(" ")

                    if (tokens.size < 2 || tokens[0] != "GET") {
                        respond(output, 400, "Only GET supported")
                        return@submit
                    }

                    val (path, query) = tokens[1].split("?", limit = 2).let {
                        it[0] to if (it.size > 1) it[1] else ""
                    }

                    val (matchedPath, handler) = routes.entries.find { matchPath(it.key, path) }
                        ?.toPair() ?: run {
                        respond(output, 404, "Route not found")
                        return@submit
                    }

                    val pathParams = extractPathParams(matchedPath, path)
                    val queryParams = parseQuery(query)

                    try {
                        val result = invokeHandler(handler, pathParams, queryParams)
                        val json = toJson(result).toJsonString()
                        respond(output, 200, json)
                    } catch (e: Exception) {
                        respond(output, 500, "Internal error: ${e.message}")
                    }
                }
            }
        }
    }

    /**
     * Escreve a resposta HTTP com código e corpo JSON.
     */
    private fun respond(out: BufferedWriter, code: Int, body: String) {
        out.write("HTTP/1.1 $code OK\r\n")
        out.write("Content-Type: application/json\r\n")
        out.write("Content-Length: ${body.toByteArray().size}\r\n")
        out.write("\r\n")
        out.write(body)
        out.flush()
    }

    /**
     * Converte a query string (`?x=1&y=2`) num mapa chave→valor.
     */
    private fun parseQuery(query: String): Map<String, String> =
        query.split("&").filter { it.contains("=") }.associate {
            val (k, v) = it.split("=", limit = 2)
            URLDecoder.decode(k, "UTF-8") to URLDecoder.decode(v, "UTF-8")
        }

    /**
     * Verifica se a rota registada coincide com o caminho do pedido.
     */
    private fun matchPath(route: String, actual: String): Boolean {
        val r = route.trim('/').split("/")
        val a = actual.trim('/').split("/")
        if (r.size != a.size) return false
        return r.zip(a).all { (rp, ap) -> rp == ap || rp.startsWith("{") }
    }

    /**
     * Extrai os valores reais das variáveis de path (ex: `{id}`) da URL.
     */
    private fun extractPathParams(route: String, actual: String): Map<String, String> {
        val r = route.trim('/').split("/")
        val a = actual.trim('/').split("/")
        return r.zip(a).filter { it.first.startsWith("{") }.associate {
            it.first.removeSurrounding("{", "}") to it.second
        }
    }

    /**
     * Executa o método correspondente ao handler com base nos parâmetros
     * extraídos do path e da query string, usando reflexão.
     */
    private fun invokeHandler(
        handler: Handler,
        pathParams: Map<String, String>,
        queryParams: Map<String, String>
    ): Any? {
        val method = handler.method
        val args = mutableMapOf<KParameter, Any?>()

        for (param in method.parameters) {
            if (param.kind == KParameter.Kind.INSTANCE) {
                args[param] = handler.instance
                continue
            }

            val name = param.name ?: continue
            val annotationPath = param.findAnnotation<Path>()
            val annotationParam = param.findAnnotation<Param>()

            val rawValue: String? = when {
                annotationPath != null -> pathParams[name]
                annotationParam != null -> queryParams[name]
                else -> null
            }

            val typedValue = convert(rawValue, param.type.classifier as? KClass<*>)
            args[param] = typedValue
        }

        return method.callBy(args)
    }

    /**
     * Converte uma string (da URL) para o tipo esperado pelo método.
     * Suporta: String, Int, Double, Boolean.
     */
    private fun convert(value: String?, type: KClass<*>?): Any? {
        if (value == null || type == null) return null
        return when (type) {
            String::class -> value
            Int::class -> value.toIntOrNull()
            Double::class -> value.toDoubleOrNull()
            Boolean::class -> value.toBooleanStrictOrNull()
            else -> null
        }
    }

    /**
     * Função auxiliar para conversão segura de booleanos.
     */
    private fun String.toBooleanStrictOrNull(): Boolean? = when (lowercase()) {
        "true" -> true
        "false" -> false
        else -> null
    }
}
