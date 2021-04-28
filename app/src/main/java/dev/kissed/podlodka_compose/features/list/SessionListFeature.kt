package dev.kissed.podlodka_compose.features.list

import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import dev.kissed.podlodka_compose.Screen
import dev.kissed.podlodka_compose.data.BookmarksRepository
import dev.kissed.podlodka_compose.data.SessionsRepository
import dev.kissed.podlodka_compose.models.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionListFeature(
  sessionsRepository: SessionsRepository,
  private val bookmarksRepository: BookmarksRepository,
  private val navControllerProvider: () -> NavHostController
) {

  data class State(
    val sessions: List<Session>,
    val bookmarks: Set<String>
  ) {
    val sessionStates: List<SessionState> by lazy {
      sessions.map {
        SessionState(
          session = it,
          isBookmarked = it.id in bookmarks
        )
      }
    }

    val sessionMap: Map<String, SessionState> = mapOf(
      *sessionStates.map { it.session.date to it }.toTypedArray()
    )

    val sessionGroups: List<SessionGroupState> by lazy {
      sessionStates
        .groupBy { it.session.date }
        .entries
        .toList()
        .map { (date, sessions) ->
          SessionGroupState(
            date = date,
            sessions = sessions
          )
        }
        .sortedBy { (date, _) -> date }
    }
  }

  data class SessionGroupState(
    val date: String,
    val sessions: List<SessionState>
  )

  data class SessionState(
    val session: Session,
    val isBookmarked: Boolean
  )

  private val mutableState = MutableStateFlow(
    State(
      bookmarks = bookmarksRepository.getBookmarksIds(),
      sessions = sessionsRepository.getAllSessions()
    )
  )
  val stateFlow: StateFlow<State> = mutableState
  val state by stateFlow::value

  fun sessionChoose(id: String) {
    navControllerProvider.invoke().navigate(
      Screen.SessionInfo.makeRoute(id)
    )
  }

  fun toggleBookmark(sessionId: String) {
    val session = state.sessionMap[sessionId]!!
    val newSession = session.copy(
      isBookmarked = !session.isBookmarked
    )
    val newBookmarks = if (newSession.isBookmarked) {
      state.bookmarks + session.session.id
    } else {
      state.bookmarks - session.session.id
    }

    bookmarksRepository.saveBookmarkIds(newBookmarks)

    mutableState.value = state.copy(
      bookmarks = newBookmarks
    )
  }
}