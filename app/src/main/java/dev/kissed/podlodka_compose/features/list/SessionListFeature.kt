package dev.kissed.podlodka_compose.features.list

import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import dev.kissed.podlodka_compose.Screen
import dev.kissed.podlodka_compose.data.SessionsRepository
import dev.kissed.podlodka_compose.models.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionListFeature(
  repository: SessionsRepository,
  private val navController: NavHostController
) {

  data class State(
    val sessions: List<Session>
  )

  private val mutableState = MutableStateFlow(
    State(
      sessions = repository.getAllSessions()
    )
  )
  val state: StateFlow<State> = mutableState

  fun sessionChoose(idx: Int) {
    navController.navigate(
      Screen.SessionInfo.makeRoute(idx)
    )
  }
}