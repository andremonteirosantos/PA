package PA_Project.annotations

/**
 * Anotação usada para indicar que um parâmetro de função
 * deve ser preenchido com um valor proveniente da query string
 * de um pedido HTTP GET.
 *
 * Exemplo:
 * ```
 * @Mapping("repeat")
 * fun repeat(@Param text: String, @Param n: Int): String = text.repeat(n)
 * ```
 *
 * Um pedido como `/api/repeat?text=PA&n=2` atribui:
 * - text = "PA"
 * - n = 2
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Param
