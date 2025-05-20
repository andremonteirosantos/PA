package PA_Project.annotations

/**
 * Anotação usada para mapear uma classe ou método a um caminho de URL.
 *
 * Pode ser aplicada a:
 * - Classes: define o prefixo de rota (ex: "api")
 * - Métodos: define o sufixo do endpoint (ex: "greet", "echo/{word}")
 *
 * Exemplo:
 * ```
 * @Mapping("api")
 * class Controller {
 *     @Mapping("greet")
 *     fun hello(): String = "Olá!"
 * }
 * ```
 *
 * O valor resultante da rota seria: `/api/greet`
 *
 * @property value O caminho (ou parte do caminho) a mapear
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Mapping(val value: String)
