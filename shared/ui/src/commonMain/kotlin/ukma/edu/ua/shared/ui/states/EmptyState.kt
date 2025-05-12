package ukma.edu.ua.shared.ui.states

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ukma.edu.ua.shared.ui.CenterColumn

@Composable
internal fun EmptyState(
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterColumn(modifier) {
        Text(
            text = "No todos yet",
            style = MaterialTheme.typography.h6
        )
        Spacer(Modifier.height(12.dp))
    }
}
