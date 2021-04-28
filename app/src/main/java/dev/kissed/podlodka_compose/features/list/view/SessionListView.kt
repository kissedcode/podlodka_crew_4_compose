package dev.kissed.podlodka_compose.features.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kissed.podlodka_compose.DI
import dev.kissed.podlodka_compose.features.list.SessionListFeature.State

@Composable
fun SessionListView() {
  val feature = DI.sessionListFeature
  val state by feature.stateFlow.collectAsState()

  SessionListView(
    state,
    onSessionClick = feature::sessionChoose,
    onBookmarkToggle = feature::toggleBookmark
  )
}

@Composable
fun SessionListView(
  state: State,
  onSessionClick: (id: String) -> Unit,
  onBookmarkToggle: (id: String) -> Unit
) {

  LazyColumn(
    Modifier
      .fillMaxSize()
      .background(Color.White),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 0.dp),
  ) {
    items(state.sessionGroups) { group ->
      Text(
        group.date,
        Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        textAlign = TextAlign.Start,
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
      )
      group.sessions.forEach { session ->
        Spacer(Modifier.size(16.dp))
        SessionView(
          session,
          { onSessionClick(session.session.id) },
          { onBookmarkToggle(session.session.id) }
        )
      }
    }
  }
}

//@Preview
//@Composable
//fun ListViewPreview() {
//  SessionListView(
//    State(
//      sessions = DI.mockSessionsRepository.getAllSessions().map {
//        SessionListFeature.SessionState(
//          session = it,
//          isBookmarked = false
//        )
//      },
//    ),
//    onSessionClick = {},
//    onBookmarkToggle = {}
//  )
//}