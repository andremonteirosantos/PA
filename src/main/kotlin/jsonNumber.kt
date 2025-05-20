package PA_Project

/**
 * Representa um número em JSON (inteiro ou decimal).
 *
 * Esta classe modela um valor numérico dentro de uma estrutura JSON,
 * podendo representar tanto números inteiros como decimais.
 *
 * @property value Valor numérico representado.
 */
data class JsonNumber(val value: Number) : JsonValue {

    /**
     * Converte o número para a sua representação textual no formato JSON.
     *
     * @return A cadeia de caracteres que representa o número, tal como seria em JSON.
     */
    override fun toJsonString(): String = value.toString()

    /**
     * Aceita um visitante que implementa [JsonVisitor], permitindo
     * a aplicação do padrão *Visitor* a esta instância de [JsonNumber].
     *
     * @param visitor O visitante a ser aceite.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
    }
}