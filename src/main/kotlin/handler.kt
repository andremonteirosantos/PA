package PA_Project

import kotlin.reflect.KFunction

/**
 * Representa a ligação entre uma instância de controlador
 * e um dos seus métodos anotados com `@Mapping`.
 *
 * Esta estrutura é usada internamente pelo framework GetJson
 * para guardar as rotas disponíveis e os respetivos handlers.
 *
 * @property instance A instância da classe do controlador
 * @property method O método a invocar quando a rota é chamada
 */
data class Handler(
    val instance: Any,
    val method: KFunction<*>
)
