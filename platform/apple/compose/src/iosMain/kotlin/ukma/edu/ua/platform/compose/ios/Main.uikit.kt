@file:OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)

package ukma.edu.ua.platform.compose.ios

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.autoreleasepool
import kotlinx.cinterop.cstr
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCValues
import platform.Foundation.NSStringFromClass
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationDelegateProtocol
import platform.UIKit.UIApplicationDelegateProtocolMeta
import platform.UIKit.UIApplicationMain
import platform.UIKit.UIResponder
import platform.UIKit.UIResponderMeta
import platform.UIKit.UIScreen
import platform.UIKit.UIWindow
import ukma.edu.ua.core.getPlatformInfo
import ukma.edu.ua.shared.ui.TodoAppTheme
import ukma.edu.ua.shared.ui.TodoListScreen

public fun main() {
    val args = emptyArray<String>()
    memScoped {
        val argc = args.size + 1
        val argv = (arrayOf("skikoApp") + args).map { it.cstr.ptr }.toCValues()
        autoreleasepool {
            UIApplicationMain(
                argc,
                argv,
                null,
                NSStringFromClass(TodoAppDelegate)
            )
        }
    }
}

public class TodoAppDelegate : UIResponder, UIApplicationDelegateProtocol {
    public companion object : UIResponderMeta(), UIApplicationDelegateProtocolMeta

    @OverrideInit
    public constructor() : super()

    private var _window: UIWindow? = null
    override fun window(): UIWindow? = _window
    override fun setWindow(window: UIWindow?) {
        _window = window
    }

    override fun application(
        application: UIApplication,
        didFinishLaunchingWithOptions: Map<Any?, *>?
    ): Boolean {
        window = UIWindow(frame = UIScreen.Companion.mainScreen.bounds)
        val params = getPlatformInfo("Skiko App")
        window!!.rootViewController = ComposeUIViewController {
            TodoAppTheme(params = params) {
                TodoListScreen(
                    modifier = Modifier.padding(top = 60.dp)
                )
            }
        }
        window!!.makeKeyAndVisible()
        return true
    }
}
