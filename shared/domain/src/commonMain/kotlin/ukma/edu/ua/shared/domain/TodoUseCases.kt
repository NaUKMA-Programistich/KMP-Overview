package ukma.edu.ua.shared.domain

import ukma.edu.ua.shared.data.CreateTodo
import ukma.edu.ua.shared.data.ITodoService
import ukma.edu.ua.shared.data.Todo
import ukma.edu.ua.shared.data.TodoClientApi
import ukma.edu.ua.shared.data.UpdateTodo
import ukma.edu.ua.shared.data.createHttpClient

internal object TodoUseCases {
    class GetAll {
        suspend operator fun invoke(): Result<List<Todo>> = kotlin.runCatching {
            ServiceLocator.todoApiClient.readAll()
        }
    }

    class Create {
        suspend operator fun invoke(
            title: String,
            completed: Boolean
        ): Result<Todo> = kotlin.runCatching {
            val createTodo = CreateTodo(
                title = title,
                completed = completed
            )
            ServiceLocator.todoApiClient.create(createTodo)
        }
    }

    class Delete {
        suspend operator fun invoke(id: Int): Result<Unit> = kotlin.runCatching {
            ServiceLocator.todoApiClient.delete(id)
        }
    }

    class Update {
        suspend operator fun invoke(
            id: Int,
            title: String,
            completed: Boolean
        ): Result<Unit> = kotlin.runCatching {
            val updateTodo = UpdateTodo(
                title = title,
                completed = completed
            )
            ServiceLocator.todoApiClient.update(id, updateTodo)
        }
    }
}

// Replace by DI
private object ServiceLocator {
    val todoApiClient: ITodoService by lazy {
        val httpClient = createHttpClient()
        TodoClientApi(httpClient = httpClient)
    }
}
