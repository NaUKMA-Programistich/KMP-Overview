package ukma.edu.ua.shared.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ukma.edu.ua.shared.data.Todo

public class TodoViewModel : ViewModel() {

    private val stateFlow = MutableStateFlow<TodoUIState>(TodoUIState.Loading)
    public val state: WrappedStateFlow<TodoUIState> = WrappedStateFlow(stateFlow.asStateFlow())

    init {
        getAllTodo()
        internalUpdate()
    }

    private fun internalUpdate() {
        viewModelScope.launch {
            while (isActive) {
                delay(5_000L)
                TodoUseCases.GetAll()
                    .invoke()
                    .onSuccess { todos ->
                        if (state.value !is TodoUIState.Success) return@onSuccess
                        stateFlow.emit(TodoUIState.Success(todos))
                    }
            }
        }
    }

    private suspend fun simulateNetworkDelay() {
        delay(1 * 1000L)
    }

    private fun getStateTodos(): List<Todo> {
        return when (val state = state.value) {
            is TodoUIState.Error -> listOf()
            TodoUIState.Loading -> listOf()
            is TodoUIState.Success -> state.todos
        }
    }

    public fun getAllTodo() {
        viewModelScope.launch {
            stateFlow.emit(TodoUIState.Loading)
            simulateNetworkDelay()

            TodoUseCases.GetAll().invoke()
                .onSuccess { todos ->
                    stateFlow.emit(TodoUIState.Success(todos))
                }
                .onFailure { error ->
                    print("Error: $error")
                    stateFlow.emit(TodoUIState.Error(error.message ?: "Unknown error"))
                }
        }
    }

    public fun createTodo(title: String, completed: Boolean) {
        val currentTodos = getStateTodos()
        viewModelScope.launch {
            stateFlow.emit(TodoUIState.Loading)
            simulateNetworkDelay()

            TodoUseCases.Create().invoke(title, completed)
                .onSuccess { todo ->
                    stateFlow.emit(TodoUIState.Success(currentTodos + todo))
                }
                .onFailure { error ->
                    stateFlow.emit(TodoUIState.Error(error.message ?: "Unknown error"))
                }
        }
    }

    public fun deleteTodo(id: Int) {
        val currentTodos = getStateTodos()
        viewModelScope.launch {
            stateFlow.emit(TodoUIState.Loading)
            simulateNetworkDelay()

            TodoUseCases.Delete().invoke(id)
                .onSuccess {
                    val updatedTodos = currentTodos.filter { it.id != id }
                    stateFlow.emit(TodoUIState.Success(updatedTodos))
                }
                .onFailure { error ->
                    stateFlow.emit(TodoUIState.Error(error.message ?: "Unknown error"))
                }
        }
    }

    public fun updateTodo(
        id: Int,
        title: String,
        completed: Boolean
    ) {
        val currentTodos = getStateTodos()
        viewModelScope.launch {
            stateFlow.emit(TodoUIState.Loading)
            simulateNetworkDelay()

            TodoUseCases.Update().invoke(id, title, completed)
                .onSuccess {
                    val updatedTodos = currentTodos.map { todo ->
                        if (todo.id == id) {
                            todo.copy(title = title, completed = completed)
                        } else {
                            todo
                        }
                    }
                    stateFlow.emit(TodoUIState.Success(updatedTodos))
                }
                .onFailure { error ->
                    stateFlow.emit(TodoUIState.Error(error.message ?: "Unknown error"))
                }
        }
    }
}
