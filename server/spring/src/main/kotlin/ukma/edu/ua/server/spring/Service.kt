package ukma.edu.ua.server.spring

import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.springframework.stereotype.Service
import ukma.edu.ua.shared.data.CreateTodo
import ukma.edu.ua.shared.data.ITodoService
import ukma.edu.ua.shared.data.Todo
import ukma.edu.ua.shared.data.UpdateTodo
import java.time.Instant
import kotlin.jvm.optionals.getOrNull

@Service
class TodoService(
    private val database: TodoDatabase
) : ITodoService {

    private fun TodoEntity.toTodo(): Todo = Todo(
        id = this.id,
        title = this.title,
        completed = this.completed,
        created = this.created.toKotlinInstant(),
        updated = this.updated.toKotlinInstant()
    )

    private fun CreateTodo.toEntity(time: Instant): TodoEntity = TodoEntity(
        title = this.title,
        completed = this.completed,
        created = time,
        updated = time
    )

    override suspend fun create(createTodo: CreateTodo): Todo {
        val time = Clock.System.now().toJavaInstant()
        val entity = createTodo.toEntity(time)
        return database.save(entity).toTodo()
    }

    override suspend fun read(id: Int): Todo? {
        return database.findById(id).getOrNull()?.toTodo()
    }

    override suspend fun readAll(): List<Todo> {
        return database.findAll().map { it.toTodo() }
    }

    override suspend fun update(id: Int, updateTodo: UpdateTodo) {
        val entity = database.findById(id).getOrNull()
            ?: throw IllegalArgumentException("Todo with id $id not found")

        entity.title = updateTodo.title
        entity.completed = updateTodo.completed
        entity.updated = Instant.now()

        database.save(entity)
    }

    override suspend fun delete(id: Int) {
        if (database.existsById(id)) {
            database.deleteById(id)
        } else {
            throw IllegalArgumentException("Todo with id $id not found")
        }
    }
}
