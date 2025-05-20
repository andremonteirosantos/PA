package PA_Project

import PA_Project.controllers.ControllerExample

/**
 * Função principal de arranque da Fase 3 do projeto.
 *
 * Inicializa o servidor HTTP utilizando a framework `GetJson`
 * e regista a classe `ControllerExample` como controlador de endpoints.
 *
 * Imprime as rotas disponíveis e começa a escutar pedidos HTTP GET na porta 8080.
 */
fun main() {
    val app = GetJson(ControllerExample::class)
    app.printRoutes()
    app.start(8080)
}