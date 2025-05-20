package PA_Project

/**
 * Representa um valor booleano em JSON.
 *
 * Um valor booleano em JSON pode ser `true` (verdadeiro) ou `false` (falso).
 *
 * @property value O valor booleano representado.
 */
data class JsonBoolean(val value: Boolean) : JsonValue {

    /**
     * Converte o valor booleano para a sua representação como texto em formato JSON.
     *
     * @return `"true"` se o valor for verdadeiro, ou `"false"` caso contrário.
     */
    override fun toJsonString(): String = value.toString()

    /**
     * Aceita um visitante que implementa [JsonVisitor], permitindo
     * a aplicação do padrão *Visitor* a esta instância de [JsonBoolean].
     *
     * @param visitor O visitante a ser aceite.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
    }
}

