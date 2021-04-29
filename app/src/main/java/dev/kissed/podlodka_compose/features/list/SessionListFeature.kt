package dev.kissed.podlodka_compose.features.list

import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import dev.kissed.podlodka_compose.app.Screen
import dev.kissed.podlodka_compose.data.BookmarksRepository
import dev.kissed.podlodka_compose.data.SessionsRepository
import dev.kissed.podlodka_compose.models.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SessionListFeature(
  private val sessionsRepository: SessionsRepository,
  private val bookmarksRepository: BookmarksRepository,
  private val navControllerProvider: () -> NavHostController,
  private val scope: CoroutineScope,
) {

  sealed class News {
    object TooManyBookmarks : News()
    object BackendProblem : News()
  }

  private val mutableState = MutableStateFlow(
    SessionListState(
      bookmarkIds = bookmarksRepository.getBookmarksIds(),
      sessions = sessionsRepository.getCachedSessions(),
      searchQuery = "",
      isLoading = true,
      isError = false,
    )
  )
  val stateFlow: StateFlow<SessionListState> = mutableState
  private var state: SessionListState
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
    scope.launch {
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