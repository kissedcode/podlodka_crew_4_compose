package dev.kissed.podlodka_compose.data

interface BookmarksRepository {

  fun getBookmarksIds(): Set<String>

  fun saveBookmarkIds(ids: Set<String>)
}