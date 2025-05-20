package PA_Project

/**
 * Validador de objectos JSON que garante que todas as chaves são válidas.
 *
 * Uma chave válida deve:
 * - Não ser vazia (ou composta apenas por espaços)
 * - Não conter espaços em branco
 *
 * Esta classe implementa a interface [JsonVisitor] e percorre toda a estrutura JSON,
 * validando as chaves de todos os [JsonObject] encontrados.
 *
 * @property valid Indica se todas as chaves visitadas são válidas. É `false` caso seja encontrada alguma chave inválida.
 * @property invalidKeys Lista de chaves inválidas encontradas durante a validação.
 */
class JsonObjectKeyValidator : JsonVisitor {
    var valid = true
    val invalidKeys = mutableListOf<String>()

    /**
     * Visita um [JsonObject], valida as suas chaves e continua a travessia recursiva.
     *
     * @param json O objecto JSON a ser visitado.
     */
    override fun visit(json: JsonObject) {
        for ((key, _) in json.properties) {
            if (key.isBlank() || key.contains(" ")) {
                valid = false
                invalidKeys.add(key)
            }
        }

        json.properties.values.forEach { it.accept(this) }
    }

    /**
     * Visita um [JsonArray] e continua a travessia recursiva dos seus elementos.
     *
     * @param json O array JSON a ser visitado.
     */
    override fun visit(json: JsonArray) {
        json.elements.forEach { it.accept(this) }
    }

    override fun visit(json: JsonString) {}
    override fun visit(json: JsonNumber) {}
    override fun visit(json: JsonBoolean) {}
    override fun visit(json: JsonNull) {}
}
