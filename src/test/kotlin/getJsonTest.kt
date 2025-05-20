package tests

import PA_Project.GetJson
import PA_Project.controllers.ControllerExample

import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import java.util.concurrent.Executors

class GetJsonTest {

    companion object {
        private val client = OkHttpClient()

        @JvmStatic
        @BeforeClass
        fun startServer() {
            val executor = Executors.newSingleThreadExecutor()
            executor.submit {
                val app = GetJson(ControllerExample::class)
                app.start(8081)
            }
            Thread.sleep(500)
        }

        private fun get(url: String): String {
            val request = Request.Builder()
                .url("http://localhost:8081$url")
                .build()
            client.newCall(request).execute().use { response ->
                assertEquals(200, response.code)
                return response.body?.string()?.trim() ?: ""
            }
        }
    }

    @Test
    fun testHelloEndpoint() {
        val body = get("/api/greet")
        assertEquals("\"Olá!\"", body)
    }

    @Test
    fun testEchoEndpoint() {
        val body = get("/api/echo/teste")
        assertEquals("\"teste\"", body)
    }

    @Test
    fun testRepeatEndpoint() {
        val body = get("/api/repeat?text=PA&n=3")
        assertEquals("\"PAPAPA\"", body)
    }

    @Test
    fun testIntsEndpoint() {
        val body = get("/api/ints")
        assertEquals("[1, 2, 3]", body)
    }

    @Test
    fun testPairEndpoint() {
        val body = get("/api/pair")
        assertEquals("{\"first\": \"um\",\"second\": \"dois\"}", body)
    }

    @Test
    fun testNextEndpointIncrements() {
        val body1 = get("/api/next")
        val body2 = get("/api/next")
        val body3 = get("/api/next")

        assertEquals("1", body1)
        assertEquals("2", body2)
        assertEquals("3", body3)
    }

}
