package dev.kissed.podlodka_compose.data

import dev.kissed.podlodka_compose.models.Session

interface SessionsRepository {

  suspend fun getAllSessions(): List<Session>

  fun getCachedSessions(): List<Session>
}