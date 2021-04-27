package dev.kissed.podlodka_compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import dev.kissed.podlodka_compose.features.info.SessionInfoView
import dev.kissed.podlodka_compose.features.list.SessionListView

@Composable
fun AppView() {
  val navController = rememberNavController()
  DI.navHostController = navController

  NavHost(navController, startDestination = Screen.SessionList.baseRoute) {
    composable(Screen.SessionList.baseRoute) {
      SessionListView()
    }

    composable(
      Screen.SessionInfo.baseRoute,
      arguments = listOf(
        navArgument(Screen.SessionInfo.PARAM_IDX_INT) { type = NavType.IntType }
      )
    ) {
      val sessionIdx = it.arguments!!.getInt(Screen.SessionInfo.PARAM_IDX_INT)
      SessionInfoView(sessionIdx)
    }
  }
}