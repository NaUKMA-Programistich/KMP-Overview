package ukma.edu.ua.platform.compose.ios

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ukma.edu.ua.core.getPlatformInfo
import ukma.edu.ua.shared.ui.TodoAppTheme
import ukma.edu.ua.shared.ui.TodoListScreen

private val params = getPlatformInfo("Skiko Library")

public fun mainIos(): UIViewController = ComposeUIViewController {
    TodoAppTheme(params = params) {
        TodoListScreen()
    }
}
