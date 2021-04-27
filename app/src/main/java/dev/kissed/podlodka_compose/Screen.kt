package dev.kissed.podlodka_compose

sealed class Screen {
  abstract val baseRoute: String

  object SessionList : Screen() {
    override val baseRoute: String = "list"
  }

  object SessionInfo : Screen() {

    const val PARAM_IDX_INT = "idx"

    override val baseRoute: String = "info/{$PARAM_IDX_INT}"

    fun makeRoute(sessionIdx: Int): String =
      baseRoute.replace("{$PARAM_IDX_INT}", sessionIdx.toString())
  }
}