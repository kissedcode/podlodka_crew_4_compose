package dev.kissed.podlodka_compose

import androidx.navigation.NavHostController
import dev.kissed.podlodka_compose.data.MockSessionsRepository
import dev.kissed.podlodka_compose.data.SessionsRepository
import dev.kissed.podlodka_compose.features.list.SessionListFeature

object DI {

  lateinit var navHostController: NavHostController

  val mockSessionsRepository: MockSessionsRepository by lazy {
    MockSessionsRepository()
  }

  val sessionsRepository: SessionsRepository by lazy {
    mockSessionsRepository
  }

  val sessionListFeature: SessionListFeature by lazy {
    SessionListFeature(
      sessionsRepository,
      navHostController
    )
  }
}