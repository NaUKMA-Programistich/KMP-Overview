import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ukma.edu.ua.shared.data.Todo
import ukma.edu.ua.shared.ui.CompletedTodo
import ukma.edu.ua.shared.ui.DeleteTodo
import ukma.edu.ua.shared.ui.states.TodoEditDialog

@Composable
internal fun SuccessState(
    todos: List<Todo>,
    onUpdate: (Int, String, Boolean) -> Unit,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var editingTodo by rememberSaveable { mutableStateOf<Todo?>(null) }

    editingTodo?.let { todo ->
        TodoEditDialog(
            todo = todo,
            onUpdate = onUpdate,
            onDelete = onDelete,
            onDismiss = { editingTodo = null }
        )
    }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(todos, key = { it.id }) { todo ->
            Item(
                todo = todo,
                onUpdate = onUpdate,
                onDelete = onDelete,
                onChangeEditableTodo = { editingTodo = it }
            )
        }
    }
}

@Composable
private fun Item(
    todo: Todo,
    onUpdate: (Int, String, Boolean) -> Unit,
    onDelete: (Int) -> Unit,
    onChangeEditableTodo: (Todo) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onChangeEditableTodo(todo) }
                .padding(16.dp)
        ) {
            CompletedTodo(todo, onUpdate)
            Text(
                text = "${todo.title}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f)
            )
            DeleteTodo(onDelete, todo)
        }
    }
}