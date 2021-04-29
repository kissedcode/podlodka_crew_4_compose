package dev.kissed.podlodka_compose.theming

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import dev.kissed.podlodka_compose.R

object AppColors {

  @Composable
  fun background(): Color = colorResource(R.color.background)

  @Composable
  fun surface(): Color = colorResource(R.color.surface)

  @Composable
  fun primary(): Color = colorResource(R.color.primary)

  @Composable
  fun primaryVariant(): Color = colorResource(R.color.primary_variant)

  @Composable
  fun secondary(): Color = colorResource(R.color.secondary)

  @Composable
  fun onPrimary(): Color = colorResource(R.color.on_primary)

  @Composable
  fun onSecondary(): Color = colorResource(R.color.on_secondary)

  @Composable
  fun onBackground(): Color = colorResource(R.color.on_background)

  @Composable
  fun onSurface(): Color = colorResource(R.color.on_surface)

  @Composable
  private fun resolve(colors: Pair<Color, Color>): Color =
    if (!isSystemInDarkTheme()) colors.first else colors.second

  @Composable
  fun bookmarkChecked(): Color = resolve(Color(0xFFF44336) to Color(0xFFE57373))
}