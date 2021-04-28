package dev.kissed.podlodka_compose.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
  primary = Color(0xFF9E9E9E),
  primaryVariant = Color(0xFF616161),
  secondary = Color(0xFFFFC107),
  background = Color.White,
  surface = Color.White,
  onPrimary = Color.White,
  onSecondary = Color.Black,
  onBackground = Color.Black,
  onSurface = Color.Black,
)

private val DarkColorPalette = darkColors(
  primary = Color(0xFF795548),
  primaryVariant = Color(0xFF5D4037),
  secondary = Color(0xFFCDDC39),
  background = Color.DarkGray,
  surface = Color.DarkGray,
  onPrimary = Color.White,
  onSecondary = Color.Black,
  onBackground = Color.White,
  onSurface = Color.White,
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    typography = typography,
    shapes = shapes,
    content = content
  )
}