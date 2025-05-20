package PA_Project

/**
 * Interface do padrão Visitor para percorrer estruturas JSON.
 *
 * Esta interface define os métodos necessários para implementar o padrão *Visitor*,
 * permitindo a manipulação de diferentes tipos de valores dentro de uma
 * estrutura JSON. Cada tipo de valor JSON (como `JsonString`, `JsonNumber`, etc.)
 * tem um metodo `visit` correspondente.
 */
interface JsonVisitor {
    fun visit(json: JsonString)
    fun visit(json: JsonNumber)
    fun visit(json: JsonBoolean)
    fun visit(json: JsonNull)
    fun visit(json: JsonArray)
    fun visit(json: JsonObject)
}
