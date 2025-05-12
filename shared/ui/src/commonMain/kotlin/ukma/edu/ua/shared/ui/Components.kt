package ukma.edu.ua.shared.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ukma.edu.ua.core.Platform
import ukma.edu.ua.shared.data.Todo
import ukma.edu.ua.shared.domain.TodoUIState

public val LocalPlatformProvider: ProvidableCompositionLocal<Platform> = compositionLocalOf {
    error("No platform provided")
}

@Composable
internal fun TopBar(
    modifier: Modifier = Modifier,
    state: TodoUIState,
    onCreateTodo: () -> Unit,
    onRefresh: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                val platform = LocalPlatformProvider.current
                Text(
                    text = "Todo List",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = platform.name,
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        },
        actions = {
            OutlinedButton(
                onClick = onRefresh,
                enabled = state !is TodoUIState.Loading,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Refresh")
            }

            OutlinedButton(
                onClick = onCreateTodo,
                enabled = state is TodoUIState.Success,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Add")
            }
        }
    )
}

@Composable
internal fun CenterColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = modifier.fillMaxSize().padding(16.dp),
    content = content
)

@Composable
internal fun DeleteTodo(onDelete: (Int) -> Unit, todo: Todo) {
    TextButton(
        onClick = { onDelete(todo.id) },
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = MaterialTheme.colors.error,
            contentColor = Color.White
        )
    ) {
        Text("Delete")
    }
}

@Composable
internal fun CompletedTodo(
    todo: Todo,
    onUpdate: (Int, String, Boolean) -> Unit
) {
    RadioButton(
        selected = todo.completed,
        onClick = { onUpdate(todo.id, todo.title, !todo.completed) }
    )
}
