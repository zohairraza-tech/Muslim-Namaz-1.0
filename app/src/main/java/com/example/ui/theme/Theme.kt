package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = Emerald500,
    onPrimary = Midnight950,
    primaryContainer = Emerald800,
    onPrimaryContainer = Emerald100,
    secondary = Gold500,
    onSecondary = Midnight950,
    secondaryContainer = Gold800,
    onSecondaryContainer = Gold100,
    tertiary = Teal500,
    background = Midnight950,
    surface = Midnight900,
    surfaceVariant = Midnight800,
    onBackground = PureWhite,
    onSurface = PureWhite,
    onSurfaceVariant = GrayDarkText,
    outline = CardBorderDark
  )

private val LightColorScheme =
  lightColorScheme(
    primary = Emerald800,
    onPrimary = PureWhite,
    primaryContainer = Emerald100,
    onPrimaryContainer = Emerald900,
    secondary = Gold700,
    onSecondary = PureWhite,
    secondaryContainer = Gold100,
    onSecondaryContainer = Gold800,
    tertiary = Teal700,
    background = OffWhite,
    surface = PureWhite,
    surfaceVariant = Emerald50,
    onBackground = Midnight900,
    onSurface = Midnight900,
    onSurfaceVariant = GrayText,
    outline = CardBorderLight
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Set false to preserve our custom Islamic brand palette
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

