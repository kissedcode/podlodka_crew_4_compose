package dev.kissed.podlodka_compose.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.kissed.podlodka_compose.theming.AppColors

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

  val themeColors = Colors(
    primary = AppColors.primary(),
    primaryVariant = AppColors.primaryVariant(),
    secondary = AppColors.secondary(),
    secondaryVariant = AppColors.secondary(),
    background = AppColors.background(),
    surface = AppColors.surface(),
    onPrimary = AppColors.onPrimary(),
    onSecondary = AppColors.onSecondary(),
    onBackground = AppColors.onBackground(),
    onSurface = AppColors.onSurface(),
    isLight = !darkTheme,
    error = if (darkTheme) Color(0xFFCF6679) else Color(0xFFB00020),
    onError = if (darkTheme) Color.White else Color.Black
  )

  MaterialTheme(
    colors = themeColors,
    typography = typography,
    shapes = shapes,
    content = content
  )
}