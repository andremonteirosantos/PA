package PA_Project

import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Converte qualquer objeto Kotlin numa representação JSON utilizando reflexão.
 *
 * Esta função suporta a conversão de diversos tipos de dados Kotlin em valores JSON,
 * incluindo tipos primitivos, enumerações, listas, arrays, mapas com chaves do tipo String,
 * e classes de dados. A conversão é realizada recorrendo à reflexão.
 *
 * Tipos suportados:
 * - Tipos primitivos como [String], [Number], [Boolean]
 * - Enumerações (os valores das enumerações são convertidos para [JsonString])
 * - Listas e arrays (convertidos para [JsonArray])
 * - Mapas com chaves do tipo [String] (convertidos para [JsonObject])
 * - Classes de dados (convertidas para [JsonObject], usando reflexão para acessar as propriedades)
 *
 * @param value O valor a converter para uma representação JSON.
 * @return A representação em JSON do valor fornecido.
 * @throws IllegalArgumentException Caso o tipo não seja suportado.
 */

fun toJson(value: Any?): JsonValue {
    return when (value) {
        null -> JsonNull
        is String -> JsonString(value)
        is Number -> JsonNumber(value)
        is Boolean -> JsonBoolean(value)
        is Enum<*> -> JsonString(value.name)
        is List<*> -> JsonArray(value.map { toJson(it) })
        is Array<*> -> JsonArray(value.toList().map { toJson(it) })
        is Map<*, *> -> {
            val jsonMap = value.entries.associate { (k, v) ->
                require(k is String) { "Only String keys are supported in maps" }
                k to toJson(v)
            }
            JsonObject(jsonMap)
        }
        else -> {
            val kClass = value::class
            if (kClass.isData) {
                val props = kClass.memberProperties.associate { prop ->
                    prop.isAccessible = true
                    prop.name to toJson(prop.getter.call(value))
                }
                JsonObject(props)
            } else {
                throw IllegalArgumentException("Unsupported type: ${value::class}")
            }
        }
    }
}
