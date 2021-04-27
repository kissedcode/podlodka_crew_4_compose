package dev.kissed.podlodka_compose.features.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kissed.podlodka_compose.DI
import dev.kissed.podlodka_compose.models.Session

@Composable
fun SessionView(session: Session, onClick: () -> Unit) {
  Box(
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .background(Color.White)
      .clickable(onClick = onClick)
  ) {
    Text(
      session.description,
      Modifier.fillMaxWidth(),
      textAlign = TextAlign.Start,
      fontSize = 20.sp
    )
  }
}

@Preview
@Composable
private fun SessionViewPreview() {
  SessionView(
    session = DI.sessionsRepository.getAllSessions().random(),
    onClick = {}
  )
}