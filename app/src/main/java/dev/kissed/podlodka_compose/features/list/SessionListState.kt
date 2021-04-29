package dev.kissed.podlodka_compose.features.list

import dev.kissed.podlodka_compose.models.Session

data class SessionListState(
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