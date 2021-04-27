package dev.kissed.podlodka_compose.features.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kissed.podlodka_compose.DI
import dev.kissed.podlodka_compose.features.list.SessionListFeature.State

@Composable
fun SessionListView() {
  val feature = DI.sessionListFeature
  val state by feature.state.collectAsState()

  SessionListView(
    state,
    onSessionClick = feature::sessionChoose
  )
}

@Composable
fun SessionListView(state: State, onSessionClick: (idx: Int) -> Unit) {

  LazyColumn(
    Modifier
      .fillMaxSize()
      .background(Color.LightGray),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(8.dp),
  ) {
    state.sessions.forEachIndexed { idx, session ->
      item {
        SessionView(session) { onSessionClick(idx) }
      }
    }
  }
}

@Preview
@Composable
fun ListViewPreview() {
  SessionListView(
    State(
      sessions = DI.sessionsRepository.getAllSessions(),
    ),
    onSessionClick = {}
  )
}