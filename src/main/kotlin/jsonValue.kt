package PA_Project

/**
 * Interface base para todos os valores JSON possíveis.
 *
 * Esta interface define o comportamento comum para todos os tipos de valores JSON,
 * implementando o padrão de *Visitor* e a função de serialização para a conversão
 * de valores JSON em uma string válida.
 */
interface JsonValue {

    /**
     * Serializa este valor JSON como uma `String` válida em formato JSON.
     *
     * A implementação desta função deve garantir que o valor seja convertido para
     * uma representação de texto que siga a especificação JSON.
     *
     * @return Uma string que representa o valor JSON.
     */
    fun toJsonString(): String

    /**
     * Aceita um visitante que pode realizar operações sobre esta estrutura JSON.
     *
     * O visitante deve ser implementado conforme o padrão *Visitor*, permitindo
     * operações específicas para cada tipo de valor JSON.
     *
     * @param visitor O visitante a ser aplicado.
     */
    fun accept(visitor: JsonVisitor)
}
