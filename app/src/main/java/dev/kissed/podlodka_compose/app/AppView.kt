package dev.kissed.podlodka_compose.app

import androidx.activity.compose.BackHandler
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import dev.kissed.podlodka_compose.features.info.SessionInfoView
import dev.kissed.podlodka_compose.features.list.view.SessionListView

@Composable
fun AppView() {
  val navController = rememberNavController()
  App.INSTANCE.di.updateNavHostController(navController)

  val backConfirm = remember { mutableStateOf(false) }

  BackHandler(
    onBack = {
      backConfirm.value = true
    }
  )

  AppTheme {
    if (backConfirm.value) {
      AlertDialog(
        onDismissRequest = {
          backConfirm.value = false
        },
        title = {
          Text("Вы уверены, что хотите выйти из приложения?")
        },
        confirmButton = {
          Button(
            onClick = {
              App.INSTANCE.di.appActivity?.finish()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
          ) {
            Text("Да")
          }
        },
        dismissButton = {
          Button(
            onClick = {
              backConfirm.value = false
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
          ) {
            Text("Отмена")
          }
        },
        backgroundColor = MaterialTheme.colors.surface
      )
    }

    NavHost(navController, startDestination = Screen.SessionList.baseRoute) {
      composable(Screen.SessionList.baseRoute) {
        SessionListView()
      }

      composable(
        Screen.SessionInfo.baseRoute,
        arguments = listOf(
          navArgument(Screen.SessionInfo.PARAM_ID_STRING) { type = NavType.StringType }
        )
      ) {
        val sessionId = it.arguments?.getString(Screen.SessionInfo.PARAM_ID_STRING)
        sessionId!!
        SessionInfoView(sessionId)
      }
    }
  }
}