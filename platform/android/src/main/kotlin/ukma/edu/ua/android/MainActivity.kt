package ukma.edu.ua.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ukma.edu.ua.core.getPlatformInfo
import ukma.edu.ua.shared.ui.TodoAppTheme
import ukma.edu.ua.shared.ui.TodoListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val params = getPlatformInfo("")
        setContent {
            TodoAppTheme(params = params) {
                TodoListScreen()
            }
        }
    }
}
