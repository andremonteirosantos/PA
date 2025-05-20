import PA_Project.*
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.math.BigDecimal

class JsonSerializationTests {
    @Test
    fun testJsonStringSerialization() {
        val json = JsonString("Olá \"Mundo\"")
        assertEquals("\"Olá \\\"Mundo\\\"\"", json.toJsonString())
    }

    @Test
    fun testJsonNumberSerialization() {
        val json = JsonNumber(3.14)
        assertEquals("3.14", json.toJsonString())
    }

    @Test
    fun testJsonBooleanSerialization() {
        assertEquals("true", JsonBoolean(true).toJsonString())
        assertEquals("false", JsonBoolean(false).toJsonString())
    }

    @Test
    fun testJsonNullSerialization() {
        assertEquals("null", JsonNull.toJsonString())
    }

    @Test
    fun testJsonArraySerialization() {
        val array = JsonArray(listOf(JsonNumber(1), JsonString("dois"), JsonBoolean(false)))
        assertEquals("[1, \"dois\", false]", array.toJsonString())
    }

    @Test
    fun testJsonObjectSerialization() {
        val obj = JsonObject(
            mapOf(
                "nome" to JsonString("André"),
                "idade" to JsonNumber(30),
                "ativo" to JsonBoolean(true)
            )
        )
        val jsonStr = obj.toJsonString()
        assertEquals("{\"nome\": \"André\",\"idade\": 30,\"ativo\": true}", jsonStr)
    }

    @Test
    fun testJsonArrayFilter() {
        val array = JsonArray(listOf(JsonNumber(1), JsonNumber(5), JsonNumber(3)))
        val filtered = array.filter {
            it is JsonNumber && (it as JsonNumber).value.toInt() > 2
        }
        assertEquals("[5, 3]", filtered.toJsonString())
    }

    @Test
    fun testJsonObjectFilter() {
        val obj = JsonObject(
            mapOf(
                "a" to JsonNumber(1),
                "b" to JsonNumber(2),
                "c" to JsonNumber(3)
            )
        )
        val filtered = obj.filter { key, _ -> key != "b" }
        assertEquals("{\"a\": 1,\"c\": 3}", filtered.toJsonString())
    }

    @Test
    fun testJsonArrayMap() {
        val array = JsonArray(listOf(JsonNumber(1), JsonNumber(2)))
        val mapped = array.map {
            val num = it as JsonNumber
            JsonNumber(BigDecimal.valueOf(num.value.toLong()) * BigDecimal.TEN)
        }
        assertEquals("[10, 20]", mapped.toJsonString())
    }

    @Test
    fun testHomogeneousArrayValidation() {
        val array = JsonArray(listOf(JsonNumber(1), JsonNumber(2), JsonNull))
        val validator = JsonTypeHomogeneityValidator()
        array.accept(validator)
        assertTrue(validator.valid)
    }

    @Test
    fun testInhomogeneousArrayFailsValidation() {
        val array = JsonArray(listOf(JsonNumber(1), JsonString("dois")))
        val validator = JsonTypeHomogeneityValidator()
        array.accept(validator)
        assertTrue(!validator.valid)
    }

    @Test
    fun testJsonObjectKeyValidator() {
        val obj = JsonObject(
            mapOf(
                "validKey" to JsonString("ok"),
                "invalid key" to JsonNumber(1),
                "" to JsonBoolean(true)
            )
        )
        val validator = JsonObjectKeyValidator()
        obj.accept(validator)

        assertTrue(!validator.valid)
        assertEquals(listOf("invalid key", ""), validator.invalidKeys)
    }
}