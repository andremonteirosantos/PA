package PA_Project.annotations

/**
 * Anotação usada para indicar que um parâmetro de função
 * deve ser preenchido com uma variável presente no caminho da URL (path).
 *
 * Exemplo:
 * ```
 * @Mapping("echo/{word}")
 * fun echo(@Path word: String): String = "$word!"
 * ```
 *
 * Um pedido como `/api/echo/teste` atribui:
 * - word = "teste"
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path
