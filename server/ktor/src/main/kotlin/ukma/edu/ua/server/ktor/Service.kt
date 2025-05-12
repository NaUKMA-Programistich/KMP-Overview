package ukma.edu.ua.server.ktor

import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ukma.edu.ua.shared.data.CreateTodo
import ukma.edu.ua.shared.data.ITodoService
import ukma.edu.ua.shared.data.Todo
import ukma.edu.ua.shared.data.UpdateTodo
import java.sql.Timestamp

class TodoService(database: Database): ITodoService {
    object Todos : Table() {
        val id = integer("id").autoIncrement()
        val title = varchar("title", length = 150)
        val completed = bool("completed")
        val created = registerColumn("created_date", InstantColumnType())
        val updated = registerColumn("updated_date", InstantColumnType())

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(Todos)
        }
    }

    override suspend fun create(createTodo: CreateTodo): Todo {
        val time = Clock.System.now()
        return dbQuery {
            val id = Todos.insert {
                it[title] = createTodo.title
                it[completed] = createTodo.completed
                it[created] = time
                it[updated] = time
            }[Todos.id]

            Todo(
                id = id,
                title = createTodo.title,
                completed = createTodo.completed,
                created = time,
                updated = time
            )
        }
    }

    override suspend fun readAll(): List<Todo> {
        return dbQuery {
            Todos.selectAll()
                .map { Todo(
                    id = it[Todos.id],
                    title = it[Todos.title],
                    completed = it[Todos.completed],
                    created = it[Todos.created],
                    updated = it[Todos.updated]
                ) }
        }
    }

    override suspend fun read(id: Int): Todo? {
        return dbQuery {
            Todos.selectAll()
                .where { Todos.id eq id }
                .map { Todo(
                    id = id,
                    title = it[Todos.title],
                    completed = it[Todos.completed],
                    created = it[Todos.created],
                    updated = it[Todos.updated]
                ) }
                .singleOrNull()
        }
    }

    override suspend fun update(id: Int, updateTodo: UpdateTodo) {
        dbQuery {
            Todos.update({ Todos.id eq id }) {
                it[title] = updateTodo.title
                it[completed] = updateTodo.completed
                it[updated] = Clock.System.now()
            }
        }
    }

    override suspend fun delete(id: Int) {
        dbQuery {
            Todos.deleteWhere {
                Todos.id.eq(id)
            }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}

class InstantColumnType : ColumnType<Instant>() {
    override fun sqlType(): String = "TIMESTAMP"

    override fun valueToDB(value: Instant?): Any? {
        if (value == null) return null
        return Timestamp.from(value.toJavaInstant())
    }

    override fun valueFromDB(value: Any): Instant = when (value) {
        is Timestamp  -> value.toInstant().toKotlinInstant()
        is java.time.Instant -> value.toKotlinInstant()
        is String -> Timestamp.valueOf(value).toInstant().toKotlinInstant()
        else -> error("Unexpected value of type ${value::class} for InstantColumnType: $value")
    }
}
