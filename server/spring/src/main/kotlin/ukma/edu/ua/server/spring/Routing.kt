package ukma.edu.ua.server.spring

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import ukma.edu.ua.shared.data.CreateTodo
import ukma.edu.ua.shared.data.Todo
import ukma.edu.ua.shared.data.UpdateTodo

@RestController
@RequestMapping("/todos")
class Routing(private val service: TodoService) {

    @GetMapping(
        "/{id}",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.OK)
    suspend fun getTodo(@PathVariable id: Int): Todo {
        return service.read(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Todo with id $id not found")
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllTodos(): List<Todo> {
        return service.readAll()
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createTodo(@RequestBody todo: CreateTodo): Todo {
        return service.create(todo)
    }

    @PutMapping(
        "/{id}",
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateTodo(@PathVariable id: Int, @RequestBody todo: UpdateTodo) {
        try {
            service.update(id, todo)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun deleteTodo(@PathVariable id: Int) {
        try {
            service.delete(id)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}
