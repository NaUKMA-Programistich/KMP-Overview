package ukma.edu.ua.server.ktor

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import org.jetbrains.exposed.sql.Database
import ukma.edu.ua.shared.data.CreateTodo
import ukma.edu.ua.shared.data.Todo
import ukma.edu.ua.shared.data.UpdateTodo

fun Application.configureRouting(
    database: Database
) {
    val todoService = TodoService(database)

    routing {
        get("/todos/{id}") {
            val id = call.parameters["id"]?.toInt()
                ?: throw IllegalArgumentException("Invalid ID")

            val todo: Todo? = todoService.read(id)
            if (todo != null) {
                call.respond(HttpStatusCode.OK, todo)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/todos") {
            val todos = todoService.readAll()
            call.respond(HttpStatusCode.OK, todos)
        }

        post("/todos") {
            val todo = call.receive<CreateTodo>()
            val id = todoService.create(todo)
            call.respond(HttpStatusCode.Created, id)
        }

        put("/todos/{id}") {
            val id = call.parameters["id"]?.toInt()
                ?: throw IllegalArgumentException("Invalid ID")

            val todo = call.receive<UpdateTodo>()
            todoService.update(id, todo)
            call.respond(HttpStatusCode.OK)
        }

        delete("/todos/{id}") {
            val id = call.parameters["id"]?.toInt()
                ?: throw IllegalArgumentException("Invalid ID")

            todoService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}
