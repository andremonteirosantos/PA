package PA_Project

/**
 * Representa o valor `null` em JSON.
 *
 * Este objecto singleton modela o valor nulo (`null`) numa estrutura JSON.
 */
object JsonNull : JsonValue {

    /**
     * Converte o valor nulo para a sua representação textual no formato JSON.
     *
     * @return A cadeia de caracteres `"null"`.
     */
    override fun toJsonString(): String = "null"

    /**
     * Aceita um visitante que implementa [JsonVisitor], permitindo
     * a aplicação do padrão *Visitor* a esta instância de [JsonNull].
     *
     * @param visitor O visitante a ser aceite.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
    }
}

