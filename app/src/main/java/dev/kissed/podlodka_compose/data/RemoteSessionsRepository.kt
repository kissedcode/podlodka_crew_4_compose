package dev.kissed.podlodka_compose.data

import dev.kissed.podlodka_compose.models.Session
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class RemoteSessionsRepository : SessionsRepository {

  private var cached: List<Session> = listOf()

  override suspend fun getAllSessions(): List<Session> {
    val sessionsRaw: String =
      HttpClient().get(
        "https://gist.githubusercontent.com/" +
            "AJIEKCX/" +
            "901e7ae9593e4afd136abe10ca7d510f/" +
            "raw/" +
            "61e7c1f037345370cf28b5ae6fdaffdd9e7e18d5/" +
            "Sessions.json"
      )

    val sessions: List<Session> = Json.decodeFromString(sessionsRaw)
    cached = sessions

    return sessions
  }

  override fun getCachedSessions(): List<Session> = cached
}