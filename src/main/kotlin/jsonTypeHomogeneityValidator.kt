package PA_Project

/**
 * Validador que verifica se todos os elementos de um array JSON são do mesmo tipo.
 *
 * Este validador percorre os elementos de um array JSON e garante que todos os
 * elementos (não nulos) sejam do mesmo tipo. Caso contrário, a validação falha.
 */
class JsonTypeHomogeneityValidator : JsonVisitor {
    var valid = true

    /**
     * Visita um [JsonArray], verifica se todos os elementos não nulos são do mesmo tipo
     * e define a propriedade [valid] como `false` caso contrário.
     *
     * @param json O array JSON a ser visitado.
     */
    override fun visit(json: JsonArray) {
        val nonNullTypes = json.elements
            .filterNot { it is JsonNull }
            .map { it::class }
            .distinct()
        if (nonNullTypes.size > 1) {
            valid = false
        }
    }

    override fun visit(json: JsonObject) {}
    override fun visit(json: JsonString) {}
    override fun visit(json: JsonNumber) {}
    override fun visit(json: JsonBoolean) {}
    override fun visit(json: JsonNull) {}
}
