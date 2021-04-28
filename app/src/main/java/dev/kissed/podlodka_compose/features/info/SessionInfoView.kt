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
import kotlin.random.Random

@Composable
fun SessionInfoView(sessionId: String) {
  Box(
    Modifier
      .fillMaxSize()
      .background(Color.Yellow)
  ) {
    Text(
      DI.sessionsRepository.getAllSessions().find { it.id == sessionId }!!.speaker
    )
  }
}

@Preview
@Composable
private fun SessionInfoPreview() {
  SessionInfoView(
    DI.mockSessionsRepository.getAllSessions().random().id
  )
}