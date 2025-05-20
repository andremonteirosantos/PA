package PA_Project

/**
 * Representa uma string em JSON.
 *
 * Uma string JSON é uma sequência de caracteres delimitada por aspas.
 * Os caracteres especiais dentro da string, como as aspas, são escapados.
 *
 * @property value O valor da string.
 */
data class JsonString(val value: String) : JsonValue {

    /**
     * Converte a string para a sua representação textual no formato JSON.
     *
     * As aspas dentro da string são escapadas adequadamente, e a string é
     * delimitada por aspas duplas.
     *
     * @return A string representada em formato JSON.
     */
    override fun toJsonString(): String {
        val escaped = value.replace("\"", "\\\"")
        return "\"$escaped\""
    }

    /**
     * Aceita um visitante que implementa [JsonVisitor], permitindo
     * a aplicação do padrão *Visitor* a esta instância de [JsonString].
     *
     * @param visitor O visitante a ser aceite.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
    }
}

