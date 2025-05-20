# GetJson Framework + JSON Library (Kotlin)

Projeto final da unidade curricular **Programação Avançada** (2024/2025).  
Desenvolvido em Kotlin, sem uso de frameworks externos, este projeto divide-se em duas partes:

- 📦 Uma **biblioteca de manipulação e serialização de JSON**
- 🌐 Um **mini-framework HTTP/GET** estilo REST para devolver objetos Kotlin em JSON

---

## 📘 Parte 1 – Biblioteca JSON em memória

### ✅ Funcionalidades

- Representação de todos os tipos JSON:
    - `JsonObject`, `JsonArray`, `JsonString`, `JsonNumber`, `JsonBoolean`, `JsonNull`
- Serialização para `String` com `toJsonString()`
- Manipulação funcional:
    - `filter()` em objetos e arrays
    - `map()` em arrays
- Visitor Pattern:
    - `JsonTypeHomogeneityValidator`: verifica homogeneidade em arrays
    - `JsonObjectKeyValidator`: valida duplicação de chaves
- Função `toJson(obj: Any?): JsonValue` com reflexão
    - Suporte a tipos básicos, listas, arrays e `data class`

---

## 🌐 Parte 2 – Mini-Framework HTTP `GetJson`

### ✅ Características

- Inicialização por classe(s) de controlador:
  ```kotlin
  val app = GetJson(Controller::class)
  app.start(8080)
  ```
- Anotações personalizadas:
    - `@Mapping("...")` em classes e funções
    - `@Path` para variáveis de caminho
    - `@Param` para query parameters
- Conversão automática de `String → Int`, `String → Boolean`, etc.
- Reutilização de instância de controlador (`var id = 0` é persistente)
- Resposta automática em JSON usando a biblioteca desenvolvida

---

## 🚀 Como correr

1. Cria o `main.kt` com:

```kotlin
fun main() {
    val app = GetJson(Controller::class)
    app.start(8080)
}
```

2. Executa a aplicação

3. Testa no navegador ou com `curl`:

```bash
curl http://localhost:8080/api/ints
curl http://localhost:8080/api/pair
curl http://localhost:8080/api/echo/abc
curl http://localhost:8080/api/repeat?text=PA&n=3
curl http://localhost:8080/api/next
```

---

## 🧪 Testes

- Testes unitários com `JUnit` para:
    - Serialização, mapeamento e filtro em JSON
    - Inferência de objetos com `toJson`
- Testes de integração com `OkHttp`:
    - Enviam pedidos reais HTTP e validam a resposta JSON

---
