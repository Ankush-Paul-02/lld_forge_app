package com.devmare.lld_forge_app.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = primaryGradientStart,
    secondary = primaryGradientMiddle,
    tertiary = primaryGradientEnd,
    background = appBackground,
    surface = appBackground,
    onPrimary = primaryTextColor,
    onSecondary = primaryTextColor,
    onTertiary = primaryTextColor,
    onBackground = primaryTextColor,
    onSurface = primaryTextColor,
    error = errorColor,
    onError = primaryTextColor
)

private val LightColorScheme = lightColorScheme(
    primary = primaryGradientStart,
    secondary = primaryGradientMiddle,
    tertiary = primaryGradientEnd,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = errorColor,
    onError = Color.White
)

@Composable
fun LldForgeAppTheme(
    darkTheme: Boolean = true, // Always dark by default
    dynamicColor: Boolean = false, // Toggle if dynamic theming is needed
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}