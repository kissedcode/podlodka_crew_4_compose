package dev.kissed.podlodka_compose.app

import android.app.Application

class App : Application() {

  val di: DI by lazy {
    DI()
  }

  override fun onCreate() {
    super.onCreate()
    INSTANCE = this
  }

  companion object {
    lateinit var INSTANCE: App
  }
}