package dev.kissed.podlodka_compose.data

import android.content.Context

class SharedPrefsBookmarksRepository(appContext: Context) : BookmarksRepository {

  private val prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

  override fun getBookmarksIds(): Set<String> =
    prefs.getStringSet(KEY_IDS, null) ?: emptySet()

  override fun saveBookmarkIds(ids: Set<String>) {
    prefs.edit()
      .putStringSet(KEY_IDS, ids)
      .apply()
  }

  companion object {
    private const val PREFS_NAME = "bookmarks"
    private const val KEY_IDS = "ids"
  }
}