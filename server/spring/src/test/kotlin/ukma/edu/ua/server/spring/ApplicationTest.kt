package ukma.edu.ua.server.spring

import kotlinx.datetime.Clock
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ukma.edu.ua.shared.data.CreateTodo
import ukma.edu.ua.shared.data.Todo
import ukma.edu.ua.shared.data.UpdateTodo


@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var json: Json

    @Test
    fun testRoot() {
        val newTodo = CreateTodo("Test Todo Spring", false)
        val createResponse = mockMvc.perform(
            post("/todos")
                .content(json.encodeToString(CreateTodo.serializer(), newTodo))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andReturn()
            .response

        val createdTodo = Todo(
            id = 1,
            title = "Test Todo Spring",
            completed = false,
            created = Clock.System.now(),
            updated = Clock.System.now()
        )

        val getResponse = mockMvc.perform(
            get("/todos/${createdTodo.id}")
        )
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val fetchedTodo = json.decodeFromString(Todo.serializer(), getResponse)

        assertEquals(createdTodo.id, fetchedTodo.id)
        assertEquals(createdTodo.title, fetchedTodo.title)
        assertEquals(createdTodo.completed, fetchedTodo.completed)

        val listResponse = mockMvc.perform(
            get("/todos")
        )
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val all = json.decodeFromString(ListSerializer(Todo.serializer()), listResponse)

        assertTrue(all.any { it.id == createdTodo.id })

        val updated = UpdateTodo("Updated Test Todo Spring", true)
        mockMvc.perform(
            put("/todos/${createdTodo.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.encodeToString(UpdateTodo.serializer(), updated))
        )
            .andExpect(status().isOk)

        val updatedResponse = mockMvc.perform(
            get("/todos/${createdTodo.id}")
        )
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val fetchedUpdated = json.decodeFromString(Todo.serializer(), updatedResponse)

        assertEquals("Updated Test Todo Spring", fetchedUpdated.title)
        assertTrue(fetchedUpdated.completed)

        mockMvc.perform(
            delete("/todos/${createdTodo.id}")
        )
            .andExpect(status().isOk)

        mockMvc.perform(
            get("/todos/${createdTodo.id}")
        )
            .andExpect(status().isNotFound)
    }
}
