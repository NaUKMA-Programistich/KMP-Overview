package ukma.edu.ua.shared.ui.states

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ukma.edu.ua.shared.ui.CenterColumn

@Composable
internal fun ErrorState(
    error: String,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterColumn(modifier) {
        Text(
            text = "Error: $error",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.body1
        )
    }
}