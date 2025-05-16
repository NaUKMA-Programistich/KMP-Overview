package ukma.edu.ua.platform.compose.macos

import androidx.compose.runtime.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import platform.AppKit.NSApp
import platform.AppKit.NSApplication
import ukma.edu.ua.core.getPlatformInfo
import ukma.edu.ua.shared.ui.TodoAppTheme
import ukma.edu.ua.shared.ui.TodoListScreen

public fun main() {
    NSApplication.sharedApplication()
    val params = getPlatformInfo("Native")
    Window(
        title = "Todo List",
        size = DpSize(400.dp, 600.dp),
    ) {
        CustomViewModelStoreProvider {
            TodoAppTheme(params = params) {
                TodoListScreen()
            }
        }
    }
    NSApp?.run()
}

@Composable
public fun CustomViewModelStoreProvider(
    content: @Composable () -> Unit
) {
    val owner = remember {
        object : ViewModelStoreOwner {
            private val store = ViewModelStore()
            override val viewModelStore: ViewModelStore
                get() = store
        }
    }
    DisposableEffect(owner) {
        onDispose { owner.viewModelStore.clear() }
    }

    CompositionLocalProvider(
        LocalViewModelStoreOwner provides owner
    ) {
        content()
    }
}
