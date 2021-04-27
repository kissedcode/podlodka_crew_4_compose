package dev.kissed.podlodka_compose.data

import dev.kissed.podlodka_compose.models.Session

interface SessionsRepository {
  fun getAllSessions(): List<Session>
}