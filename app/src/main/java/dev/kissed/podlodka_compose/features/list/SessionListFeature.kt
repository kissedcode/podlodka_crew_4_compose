package dev.kissed.podlodka_compose.features.list

import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import dev.kissed.podlodka_compose.app.Screen
import dev.kissed.podlodka_compose.data.BookmarksRepository
import dev.kissed.podlodka_compose.data.SessionsRepository
import dev.kissed.podlodka_compose.models.Session
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SessionListFeature(
  private val sessionsRepository: SessionsRepository,
  private val bookmarksRepository: BookmarksRepository,
  private val navControllerProvider: () -> NavHostController
) {

  data class State(
    val sessions: List<Session>,
    val bookmarkIds: Set<String>,
    val searchQuery: String,
    val isLoading: Boolean,
    val isError: Boolean,
  ) {
    val bookmarks: List<Session> by lazy {
      sessions
        .filter { it.id in bookmarkIds }
        .sortedBy { it.date + it.timeInterval }
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
        .filter {
          it.session.speaker.contains(searchQuery, ignoreCase = true)
              || it.session.description.contains(searchQuery, ignoreCase = true)
        }
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
    object BackendProblem : News()
  }

  private val mutableState = MutableStateFlow(
    State(
      bookmarkIds = bookmarksRepository.getBookmarksIds(),
      sessions = sessionsRepository.getCachedSessions(),
      searchQuery = "",
      isLoading = true,
      isError = false,
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

  init {
    reload()
  }

  fun reload() {
    state = state.copy(
      isLoading = true,
      isError = false
    )
    GlobalScope.launch {
      val sessions: List<Session> = try {
        sessionsRepository.getAllSessions()
      } catch (e: Exception) {
        newsChannel.offer(News.BackendProblem)
        emptyList()
      }

      state = state.copy(
        isLoading = false,
        sessions = sessions,
        isError = sessions.isEmpty()
      )
    }
  }

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

  fun search(query: String) {
    state = state.copy(
      searchQuery = query
    )
  }

  companion object {
    private const val MAX_BOOKMARKS = 3
  }
}