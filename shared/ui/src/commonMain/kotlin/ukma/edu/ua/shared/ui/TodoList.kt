package ukma.edu.ua.shared.ui

import SuccessState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ukma.edu.ua.shared.domain.TodoUIState
import ukma.edu.ua.shared.domain.TodoViewModel
import ukma.edu.ua.shared.ui.states.EmptyState
import ukma.edu.ua.shared.ui.states.ErrorState
import ukma.edu.ua.shared.ui.states.LoadingState
import ukma.edu.ua.shared.ui.states.TodoCreateDialog

@Composable
public fun TodoListScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel { TodoViewModel() },
) {
    val state by viewModel.state.collectAsState()
    var isCreatingTodo by rememberSaveable { mutableStateOf<Boolean>(false) }

    if (isCreatingTodo) {
        TodoCreateDialog(
            onCreate = viewModel::createTodo,
            onDismiss = { isCreatingTodo = false }
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                state = state,
                onCreateTodo = { isCreatingTodo = true },
                onRefresh = viewModel::getAllTodo
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        TodoListContent(
            state = state,
            onRefresh = viewModel::getAllTodo,
            onUpdate = viewModel::updateTodo,
            onDelete = viewModel::deleteTodo,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        )
    }
}

@Composable
public fun TodoListContent(
    state: TodoUIState,
    onRefresh: () -> Unit,
    onUpdate: (Int, String, Boolean) -> Unit,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        is TodoUIState.Error -> ErrorState(state.message, onRefresh, modifier)
        TodoUIState.Loading -> LoadingState(modifier)
        is TodoUIState.Success -> {
            if (state.todos.isEmpty()) {
                EmptyState(onRefresh, modifier)
            } else {
                SuccessState(state.todos, onUpdate, onDelete, modifier)
            }
        }
    }
}
