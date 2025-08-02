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
    primary = gradient1,
    secondary = gradient2,
    tertiary = gradient3,
    background = backgroundColor,
    surface = backgroundColor,
    onPrimary = whiteColor,
    onSecondary = whiteColor,
    onTertiary = whiteColor,
    onBackground = whiteColor,
    onSurface = whiteColor,
    error = errorColor,
    onError = whiteColor
)

private val LightColorScheme = lightColorScheme(
    primary = gradient1,
    secondary = gradient2,
    tertiary = gradient3,
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
fun Lld_forge_appTheme(
    darkTheme: Boolean = true, // Your app is always dark by default
    dynamicColor: Boolean = false, // Disable dynamic color for consistent branding
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