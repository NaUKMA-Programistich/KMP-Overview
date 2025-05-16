package ukma.edu.ua.shared.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ukma.edu.ua.core.Platform

private val Green200 = Color(0xFFA5D6A7)
private val Green500 = Color(0xFF4CAF50)
private val Green700 = Color(0xFF388E3C)
private val Teal200 = Color(0xFF03DAC5)
private val DangerColor = Color(0xFFB00020)

private val AppTypography = Typography(
    h6 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    )
)

private val AppShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

@Composable
public fun TodoAppTheme(
    params: String,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Green200,
            primaryVariant = Green700,
            secondary = Teal200,
            background = Color.Black,
            surface = Color.DarkGray,
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White,
            error = DangerColor,
            onError = Color.White
        )
    } else {
        lightColors(
            primary = Green500,
            primaryVariant = Green700,
            secondary = Teal200,
            background = Color.White,
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.Black,
            onSurface = Color.Black,
            error = DangerColor,
            onError = Color.White
        )
    }

    CompositionLocalProvider(LocalPlatformProvider provides Platform(params)) {
        MaterialTheme(
            colors = colors,
            typography = AppTypography,
            shapes = AppShapes,
            content = content
        )
    }
}
