package PA_Project

/**
 * Representa um array JSON.
 *
 * Um array JSON é uma sequência ordenada de valores, onde cada valor
 * pode ser de qualquer tipo JSON válido.
 *
 * @property elements Lista de elementos JSON contidos no array.
 */
data class JsonArray(val elements: List<JsonValue>) : JsonValue {

    /**
     * Serializa este array JSON para uma cadeia de caracteres no formato JSON.
     *
     * @return Uma string que representa o array em formato JSON.
     */
    override fun toJsonString(): String {
        return elements.joinToString(prefix = "[", postfix = "]") { it.toJsonString() }
    }

    /**
     * Aceita um visitante [JsonVisitor], aplicando-o a este array e a todos os seus elementos.
     *
     * @param visitor O visitante a aplicar.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
        elements.forEach { it.accept(visitor) }
    }

    /**
     * Transforma os elementos do array com a função fornecida.
     *
     * @param transform Função de transformação aplicada a cada elemento.
     * @return Um novo [JsonArray] com os elementos transformados.
     */
    fun map(transform: (JsonValue) -> JsonValue): JsonArray {
        return JsonArray(elements.map(transform))
    }

    /**
     * Filtra os elementos do array de acordo com o predicado fornecido.
     *
     * @param predicate Função que determina se um elemento deve ser incluído.
     * @return Um novo [JsonArray] contendo apenas os elementos que satisfazem o predicado.
     */
    fun filter(predicate: (JsonValue) -> Boolean): JsonArray {
        return JsonArray(elements.filter(predicate))
    }
}
