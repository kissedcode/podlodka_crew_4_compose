package dev.kissed.podlodka_compose.features.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.accompanist.glide.rememberGlidePainter
import dev.kissed.podlodka_compose.app.DI
import dev.kissed.podlodka_compose.R
import dev.kissed.podlodka_compose.models.Session

@Composable
fun SessionInfoView(sessionId: String) {

  val session = remember {
    DI.sessionsRepository.getAllSessions().find { it.id == sessionId }!!
  }

  val imagePainter = rememberGlidePainter(
    request = session.imageUrl,
    requestBuilder = {
      placeholder(R.drawable.notification_bg)
      transition(DrawableTransitionOptions.withCrossFade())
      centerCrop()
    }
  )

  SessionInfoView(session = session) {
    Image(
      imagePainter,
      "Speaker ${session.speaker} avatar",
      Modifier
        .align(Alignment.CenterHorizontally)
        .size(200.dp)
        .clip(CircleShape)
    )
  }
}

@Composable
private fun SessionInfoView(session: Session, image: @Composable ColumnScope.() -> Unit) {
  Surface(
    Modifier
      .fillMaxSize()
      .background(MaterialTheme.colors.surface)
  ) {
    Box {
      Column(
        Modifier
          .align(Alignment.Center)
          .offset(y = 0.dp)
      ) {
        image()
        Text(
          session.speaker,
          Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp),
          textAlign = TextAlign.Center,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
        )
        DateTimeView(session)
        Text(
          session.description,
          Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp),
          textAlign = TextAlign.Start,
          fontSize = 17.sp,
          fontWeight = FontWeight.Normal,
        )
      }
    }
  }
}

@Composable
private fun DateTimeView(session: Session) {
  Row(
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .padding(top = 24.dp)
  ) {
    Icon(Icons.Filled.CalendarToday, contentDescription = "date time")
    Text(
      "${session.date}, ${session.timeInterval}",
      Modifier
        .fillMaxWidth()
        .padding(start = 8.dp),
      textAlign = TextAlign.Start,
      fontSize = 17.sp,
      fontWeight = FontWeight.Normal,
    )
  }
}

@Preview
@Composable
private fun SessionInfoPreview() {
  SessionInfoView(
    session = Session(
      id = "1",
      speaker = "Жека Вартанов",
      date = "19 апреля",
      timeInterval = "10.00-11.00",
      description = "Крутой доклад Крутой доклад Крутой доклад Крутой доклад !",
      imageUrl = "ya.ru"
    ),
    image = {
      Box(
        Modifier
          .size(10.dp)
          .background(Color.Red)
      )
    }
  )
}