package dev.kissed.podlodka_compose

sealed class Screen {
  abstract val baseRoute: String

  object SessionList : Screen() {
    override val baseRoute: String = "list"
  }

  object SessionInfo : Screen() {

    const val PARAM_ID_STRING = "id"

    override val baseRoute: String = "info/{$PARAM_ID_STRING}"

    fun makeRoute(sessionId: String): String =
      baseRoute.replace("{$PARAM_ID_STRING}", sessionId)
  }
}