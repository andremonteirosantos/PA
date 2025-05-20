package PA_Project

/**
 * Representa um objecto JSON, mapeando chaves para valores JSON.
 *
 * Um objecto JSON é uma coleção não ordenada de pares chave-valor,
 * onde as chaves são sempre do tipo `String` e os valores podem ser de qualquer tipo JSON válido.
 *
 * @property properties Mapa de pares chave-valor, onde as chaves são `String` e os valores são instâncias de [JsonValue].
 */
data class JsonObject(val properties: Map<String, JsonValue>) : JsonValue {

    /**
     * Converte o objecto JSON para a sua representação textual conforme a especificação JSON.
     *
     * As chaves são escapadas adequadamente com aspas, e os pares chave-valor são separados por vírgulas.
     *
     * @return Uma cadeia de caracteres no formato JSON representando este objecto.
     */
    override fun toJsonString(): String {
        return properties.entries.joinToString(
            prefix = "{", postfix = "}", separator = ","
        ) { (key, value) ->
            "\"${key.replace("\"", "\\\"")}\": ${value.toJsonString()}"
        }
    }

    /**
     * Aceita um visitante que implementa [JsonVisitor], permitindo
     * a aplicação do padrão *Visitor* a esta instância de [JsonObject]
     * e a todos os seus valores.
     *
     * @param visitor O visitante a ser aceite.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
        properties.values.forEach { it.accept(visitor) }
    }

    /**
     * Retorna um novo [JsonObject] contendo apenas os pares chave-valor
     * que satisfazem o predicado fornecido.
     *
     * @param predicate Uma função que recebe a chave e o valor e retorna `true`
     *                  se o par chave-valor deve ser incluído.
     * @return Um novo [JsonObject] filtrado, contendo apenas os pares que satisfazem o predicado.
     */
    fun filter(predicate: (String, JsonValue) -> Boolean): JsonObject {
        val filtered = properties.filter { (key, value) -> predicate(key, value) }
        return JsonObject(filtered)
    }
}
