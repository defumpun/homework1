package com.example.homework1

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColors(
    val firstColumnColor: Color,
    val secondColumnColor: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val background: Color,
    val onBackground: Color
)

val LightCustomColors = CustomColors(
    firstColumnColor = Color.Red,
    secondColumnColor = Color.Blue,
    primary = Color.Red,
    onPrimary = Color.White,
    secondary = Color.Blue,
    background = Color.White,
    onBackground = Color.Black
)

val DarkCustomColors = CustomColors(
    firstColumnColor = Color.Gray,
    secondColumnColor = Color.DarkGray,
    primary = Color.Red,
    onPrimary = Color.Black,
    secondary = Color.Cyan,
    background = Color.Black,
    onBackground = Color.White
)


val LocalCustomColors = staticCompositionLocalOf { LightCustomColors }

@Composable
fun CustomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = remember { if (darkTheme) DarkCustomColors else LightCustomColors }

    CompositionLocalProvider(LocalCustomColors provides colors) {
        content()
    }
}
