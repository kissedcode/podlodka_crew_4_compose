package dev.kissed.podlodka_compose.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent

class AppActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    App.INSTANCE.di.appActivity = this

    setContent {
      AppView()
    }
  }

  override fun onDestroy() {
    super.onDestroy()

    App.INSTANCE.di.appActivity = null
  }
}