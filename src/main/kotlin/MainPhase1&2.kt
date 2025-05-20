package PA_Project

/**
 * Função principal de demonstração das funcionalidades da Fase 1 e Fase 2:
 * - Criação e serialização de tipos JSON básicos e compostos
 * - Aplicação de `map()` e `filter()` sobre `JsonArray` e `JsonObject`
 * - Utilização do Visitor Pattern para validação de homogeneidade
 */
fun main() {
    val s = JsonString("Hello \"World\"")
    val n = JsonNumber(10)
    val b = JsonBoolean(true)
    val nul = JsonNull
    println(s.toJsonString())
    println(n.toJsonString())
    println(b.toJsonString())
    println(nul.toJsonString())

    val array = JsonArray(listOf(
        JsonNumber(1),
        JsonNumber(2),
        JsonNumber(3)
    ))
    println(array.toJsonString())

    val mapped = array.map {
        if (it is JsonNumber) JsonNumber(it.value.toInt() * 10) else it
    }
    println(mapped.toJsonString())

    val filteredArray = array.filter {
        it is JsonNumber && it.value.toInt() % 2 == 1
    }
    println(filteredArray.toJsonString())

    val obj = JsonObject(
        mapOf(
            "name" to JsonString("André"),
            "age" to JsonNumber(25),
            "active" to JsonBoolean(true)
        )
    )
    println(obj.toJsonString())

    val filteredObject = obj.filter { key, _ -> key != "age" }
    println(filteredObject.toJsonString())

    val arrayVisitor = JsonArray(listOf(JsonNumber(1), JsonNumber(2), JsonString("3")))
    val validator = JsonTypeHomogeneityValidator()
    arrayVisitor.accept(validator)
    println("Is the array homogeneous? ${validator.valid}")
}
