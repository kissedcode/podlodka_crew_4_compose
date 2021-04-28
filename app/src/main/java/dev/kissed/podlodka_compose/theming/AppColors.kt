package dev.kissed.podlodka_compose.theming

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppColors {

  @Composable
  private fun resolve(colors: Pair<Color, Color>): Color =
    if (!isSystemInDarkTheme()) colors.first else colors.second

  @Composable
  fun bookmarkChecked(): Color = resolve(Color(0xFFF44336) to Color(0xFFE57373))
}