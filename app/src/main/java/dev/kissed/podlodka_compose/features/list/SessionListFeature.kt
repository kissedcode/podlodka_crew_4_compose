package dev.kissed.podlodka_compose.features.list

import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import dev.kissed.podlodka_compose.app.Screen
import dev.kissed.podlodka_compose.data.BookmarksRepository
import dev.kissed.podlodka_compose.data.SessionsRepository
import dev.kissed.podlodka_compose.models.Session
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class SessionListFeature(
  sessionsRepository: SessionsRepository,
  private val bookmarksRepository: BookmarksRepository,
  private val navControllerProvider: () -> NavHostController
) {

  data class State(
    val sessions: List<Session>,
    val bookmarkIds: Set<String>
  ) {
    val bookmarks: List<Session> by lazy {
      sessions
        .filter { it.id in bookmarkIds }
        .sortedBy { it.date + it.timeInterval } // fixme:???
    }

    val sessionStates: List<SessionState> by lazy {
      sessions.map {
        SessionState(
          session = it,
          isBookmarked = it.id in bookmarkIds
        )
      }
    }

    /**
     * session.id to SessionState
     */
    val sessionMap: Map<String, SessionState> by lazy {
      mapOf(
        *sessionStates.map { it.session.id to it }.toTypedArray()
      )
    }

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

  sealed class News {
    object TooManyBookmarks : News()
  }

  private val mutableState = MutableStateFlow(
    State(
      bookmarkIds = bookmarksRepository.getBookmarksIds(),
      sessions = sessionsRepository.getAllSessions()
    )
  )
  val stateFlow: StateFlow<State> = mutableState
  private var state: State
    get() = stateFlow.value
    set(value) {
      mutableState.value = value
    }

  private val newsChannel = Channel<News>()
  val news: Flow<News> = newsChannel.receiveAsFlow()

  fun sessionChoose(id: String) {
    navControllerProvider.invoke().navigate(
      Screen.SessionInfo.makeRoute(id)
    )
  }

  fun toggleBookmark(sessionId: String) {
    val session = state.sessionMap[sessionId]!!
    val add = !session.isBookmarked

    if (add && state.bookmarkIds.size >= MAX_BOOKMARKS) {
      newsChannel.offer(News.TooManyBookmarks)
      return
    }

    val newBookmarks = if (add) {
      state.bookmarkIds + session.session.id
    } else {
      state.bookmarkIds - session.session.id
    }

    bookmarksRepository.saveBookmarkIds(newBookmarks)

    state = state.copy(
      bookmarkIds = newBookmarks
    )
  }

  companion object {
    private const val MAX_BOOKMARKS = 3
  }
}