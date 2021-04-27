package dev.kissed.podlodka_compose.features.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.kissed.podlodka_compose.DI
import dev.kissed.podlodka_compose.features.list.SessionListView
import kotlin.random.Random

@Composable
fun SessionInfoView(sessionIdx: Int) {
  Box(
    Modifier
      .fillMaxSize()
      .background(Color.Yellow)
  ) {
    Text(
      DI.sessionsRepository.getAllSessions()[sessionIdx].speaker
    )
  }
}

@Preview
@Composable
private fun SessionInfoPreview() {
  SessionInfoView(
    Random.nextInt(DI.mockSessionsRepository.getAllSessions().size)
  )
}