package ukma.edu.ua.shared.ui.states

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ukma.edu.ua.shared.data.Todo

@Composable
private fun TodoDialog(
    dialogTitle: String,
    initialTitle: String,
    initialCompleted: Boolean,
    confirmButtonText: String,
    onConfirm: (String, Boolean) -> Unit,
    onDelete: (() -> Unit)? = null,
    onDismiss: () -> Unit
) {
    var title by rememberSaveable { mutableStateOf(initialTitle) }
    var completed by rememberSaveable { mutableStateOf(initialCompleted) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = dialogTitle,
                    style = MaterialTheme.typography.h6
                )

                Spacer(Modifier.height(16.dp))

                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Completed")
                    Spacer(Modifier.weight(1f))
                    Checkbox(
                        checked = completed,
                        onCheckedChange = { completed = it }
                    )
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onDelete?.let {
                        TextButton(onClick = it) {
                            Text("Delete")
                        }
                    }

                    Row {
                        TextButton(onClick = onDismiss) {
                            Text("Cancel")
                        }

                        Spacer(Modifier.width(8.dp))

                        Button(
                            onClick = {
                                onConfirm(title, completed)
                                onDismiss()
                            },
                            enabled = title.isNotBlank()
                        ) {
                            Text(confirmButtonText)
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun TodoEditDialog(
    todo: Todo,
    onUpdate: (Int, String, Boolean) -> Unit,
    onDelete: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    TodoDialog(
        dialogTitle = "Edit Todo",
        initialTitle = todo.title,
        initialCompleted = todo.completed,
        confirmButtonText = "Save",
        onConfirm = { newTitle, newCompleted ->
            onUpdate(todo.id, newTitle, newCompleted)
        },
        onDelete = { onDelete(todo.id) },
        onDismiss = onDismiss
    )
}

@Composable
internal fun TodoCreateDialog(
    onCreate: (String, Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    TodoDialog(
        dialogTitle = "Create Todo",
        initialTitle = "",
        initialCompleted = false,
        confirmButtonText = "Create",
        onConfirm = onCreate,
        onDelete = null,
        onDismiss = onDismiss
    )
}
