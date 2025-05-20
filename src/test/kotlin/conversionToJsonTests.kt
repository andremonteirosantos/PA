import PA_Project.*
import org.junit.Test
import org.junit.Assert.assertEquals

data class Person(val name: String, val age: Int, val active: Boolean)

class ReflectionTests {
    @Test
    fun testToJsonSimpleTypes() {
        assertEquals(JsonString("hello"), toJson("hello"))
        assertEquals(JsonNumber(10), toJson(10))
        assertEquals(JsonBoolean(true), toJson(true))
        assertEquals(JsonNull, toJson(null))
    }

    @Test
    fun testToJsonList() {
        val list = listOf(1, 2, 3)
        val expected = JsonArray(listOf(JsonNumber(1), JsonNumber(2), JsonNumber(3)))
        assertEquals(expected, toJson(list))
    }

    @Test
    fun testToJsonObject() {
        val person = Person("Ricardo", 23, true)
        val json = toJson(person) as JsonObject
        assertEquals(JsonString("Ricardo"), json.properties["name"])
        assertEquals(JsonNumber(23), json.properties["age"])
        assertEquals(JsonBoolean(true), json.properties["active"])
    }
}