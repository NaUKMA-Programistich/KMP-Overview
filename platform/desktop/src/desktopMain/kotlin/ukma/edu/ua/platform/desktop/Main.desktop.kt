package ukma.edu.ua.platform.desktop

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import ukma.edu.ua.core.getPlatformInfo
import ukma.edu.ua.shared.ui.TodoAppTheme
import ukma.edu.ua.shared.ui.TodoListScreen

public fun main() {
    val params = getPlatformInfo("")
    singleWindowApplication(
        title = "Todo List",
        state = WindowState(
            position = WindowPosition(Alignment.Center),
            width = 400.dp,
            height = 600.dp
        )
    ) {
        TodoAppTheme(params = params) {
            TodoListScreen()
        }
    }
}
