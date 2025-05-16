package ukma.edu.ua.shared.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

public interface ITodoService {
    public suspend fun create(createTodo: CreateTodo): Todo
    public suspend fun read(id: Int): Todo?
    public suspend fun readAll(): List<Todo>
    public suspend fun update(id: Int, updateTodo: UpdateTodo)
    public suspend fun delete(id: Int)
}

public class TodoClientApi(private val httpClient: HttpClient) : ITodoService {
    override suspend fun create(createTodo: CreateTodo): Todo {
        return httpClient.post("/todos") {
            setBody(createTodo)
        }.body()
    }

    override suspend fun read(id: Int): Todo? {
        return httpClient.get("/todos/$id").body()
    }

    override suspend fun readAll(): List<Todo> {
        return httpClient.get("/todos").body()
    }

    override suspend fun update(id: Int, updateTodo: UpdateTodo) {
        return httpClient.put("/todos/$id") {
            setBody(updateTodo)
        }.body()
    }

    override suspend fun delete(id: Int) {
        httpClient.delete("/todos/$id")
    }
}
