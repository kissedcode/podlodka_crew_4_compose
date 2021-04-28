package dev.kissed.podlodka_compose

import android.content.Context
import androidx.navigation.NavHostController
import dev.kissed.podlodka_compose.data.BookmarksRepository
import dev.kissed.podlodka_compose.data.MockSessionsRepository
import dev.kissed.podlodka_compose.data.SessionsRepository
import dev.kissed.podlodka_compose.data.SharedPrefsBookmarksRepository
import dev.kissed.podlodka_compose.features.list.SessionListFeature

object DI {

  lateinit var appContext: Context

  private lateinit var navHostController: NavHostController

  fun updateNavHostController(navHostController: NavHostController) {
    this.navHostController = navHostController
  }

  private val navControllerProvider: () -> NavHostController = {
    navHostController
  }

  val mockSessionsRepository: MockSessionsRepository by lazy {
    MockSessionsRepository()
  }

  val sessionsRepository: SessionsRepository by lazy {
    mockSessionsRepository
  }

  val bookmarksRepository: BookmarksRepository by lazy {
    SharedPrefsBookmarksRepository(appContext)
  }

  val sessionListFeature: SessionListFeature by lazy {
    SessionListFeature(
      sessionsRepository,
      bookmarksRepository,
      navControllerProvider
    )
  }
}