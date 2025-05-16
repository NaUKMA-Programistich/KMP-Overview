package ukma.edu.ua.server.ktor

import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.Json
import ukma.edu.ua.shared.data.CreateTodo
import ukma.edu.ua.shared.data.Serialization
import ukma.edu.ua.shared.data.Todo
import ukma.edu.ua.shared.data.UpdateTodo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }

        val client = createClient {
            install(ContentNegotiation) {
                json(Serialization.json)
            }
        }

        val newTodoData = CreateTodo(title = "Test Todo", completed = false)
        val postResponse = client.post("/todos") {
            contentType(ContentType.Application.Json)
            setBody(newTodoData)
        }
        assertEquals(HttpStatusCode.Created, postResponse.status)

        val createdId = postResponse.body<Todo>().id
        val getResponse = client.get("/todos/$createdId")
        assertEquals(HttpStatusCode.OK, getResponse.status)

        val fetchedTodo = Json.decodeFromString<Todo>(getResponse.bodyAsText())
        assertEquals(createdId, fetchedTodo.id)
        assertEquals("Test Todo", fetchedTodo.title)
        assertFalse(fetchedTodo.completed)

        val getAllResponse = client.get("/todos")
        assertEquals(HttpStatusCode.OK, getAllResponse.status)
        val todos = Json.decodeFromString<List<Todo>>(getAllResponse.bodyAsText())
        assertTrue(todos.any { it.id == createdId })

        val updateTodoData = UpdateTodo(
            title = "Updated Test Todo",
            completed = true
        )
        val putResponse = client.put("/todos/$createdId") {
            contentType(ContentType.Application.Json)
            setBody(updateTodoData)
        }
        assertEquals(HttpStatusCode.OK, putResponse.status)

        val getUpdatedResponse = client.get("/todos/$createdId")
        assertEquals(HttpStatusCode.OK, getUpdatedResponse.status)
        val fetchedUpdatedTodo = Json.decodeFromString<Todo>(getUpdatedResponse.bodyAsText())
        assertEquals("Updated Test Todo", fetchedUpdatedTodo.title)
        assertTrue(fetchedUpdatedTodo.completed)

        val deleteResponse = client.delete("/todos/$createdId")
        assertEquals(HttpStatusCode.OK, deleteResponse.status)
        val getDeletedResponse = client.get("/todos/$createdId")
        assertEquals(HttpStatusCode.NotFound, getDeletedResponse.status)
    }
}
