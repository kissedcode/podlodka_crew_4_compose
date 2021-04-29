package dev.kissed.podlodka_compose.models

import kotlinx.serialization.Serializable

@Serializable
data class Session(
  val id: String,
  val speaker: String,
  val date: String,
  val timeInterval: String,
  val description: String,
  val imageUrl: String,
  val isFavourite: Boolean? = null // crutch for bad backend :)
)