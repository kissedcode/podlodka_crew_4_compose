package dev.kissed.podlodka_compose.app

import androidx.navigation.NavHostController
import dev.kissed.podlodka_compose.data.*
import dev.kissed.podlodka_compose.features.list.SessionListFeature

class DI {

  var appActivity: AppActivity? = null

  private lateinit var navHostController: NavHostController

  fun updateNavHostController(navHostController: NavHostController) {
    this.navHostController = navHostController
  }

  private val navControllerProvider: () -> NavHostController = {
    navHostController
  }

  private val remoteSessionsRepository: RemoteSessionsRepository by lazy {
    RemoteSessionsRepository()
  }

  val sessionsRepository: SessionsRepository by lazy {
    remoteSessionsRepository
  }

  private val bookmarksRepository: BookmarksRepository by lazy {
    SharedPrefsBookmarksRepository(appActivity!!.applicationContext)
  }

  val sessionListFeature: SessionListFeature by lazy {
    SessionListFeature(
      sessionsRepository,
      bookmarksRepository,
      navControllerProvider
    )
  }
}