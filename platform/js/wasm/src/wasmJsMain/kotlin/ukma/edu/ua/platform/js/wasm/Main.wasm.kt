package ukma.edu.ua.platform.js.wasm

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import ukma.edu.ua.core.getPlatformInfo
import ukma.edu.ua.shared.ui.TodoAppTheme
import ukma.edu.ua.shared.ui.TodoListScreen

@OptIn(ExperimentalComposeUiApi::class)
public fun main() {
    val params = getPlatformInfo("")
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        TodoAppTheme(params = params) {
            TodoListScreen()
        }
    }
}
