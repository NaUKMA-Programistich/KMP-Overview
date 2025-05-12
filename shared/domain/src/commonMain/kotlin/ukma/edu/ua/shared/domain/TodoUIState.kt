package ukma.edu.ua.shared.domain

import ukma.edu.ua.shared.data.Todo

public sealed interface TodoUIState {
    public data object Loading : TodoUIState

    public data class Success(
        val todos: List<Todo>,
    ) : TodoUIState

    public data class Error(
        val message: String,
    ) : TodoUIState
}
