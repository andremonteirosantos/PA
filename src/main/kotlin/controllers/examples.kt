package PA_Project.controllers

import PA_Project.annotations.*

/**
 * Exemplo de controlador anotado com `@Mapping`,
 * utilizado para demonstrar os principais recursos do framework GetJson.
 *
 * Os métodos expõem endpoints HTTP do tipo GET, com ou sem parâmetros.
 * O valor de `id` é mantido entre chamadas, simulando estado.
 */
@Mapping("api")
class ControllerExample {
    private var id = 0

    /**
     * Endpoint `/api/next`
     * Incrementa e retorna o valor de `id` (simula estado persistente).
     */
    @Mapping("next")
    fun next(): Int {
        id += 1
        return id
    }

    /**
     * Endpoint `/api/ints`
     * Retorna uma lista de inteiros.
     */
    @Mapping("ints")
    fun demo(): List<Int> = listOf(1, 2, 3)

    /**
     * Endpoint `/api/pair`
     * Retorna um par de strings.
     */
    @Mapping("pair")
    fun obj(): Pair<String, String> = Pair("um", "dois")

    /**
     * Endpoint `/api/greet`
     * Retorna uma saudação simples.
     */
    @Mapping("greet")
    fun hello(): String = "Olá!"

    /**
     * Endpoint `/api/echo/{word}`
     * Devolve exatamente a palavra recebida na rota.
     */
    @Mapping("echo/{word}")
    fun echo(@Path word: String): String = word

    /**
     * Endpoint `/api/repeat?text=...&n=...`
     * Repete a string `text` `n` vezes.
     */
    @Mapping("repeat")
    fun repeat(@Param text: String, @Param n: Int): String = text.repeat(n)
}
