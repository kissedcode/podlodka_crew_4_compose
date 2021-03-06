package dev.kissed.podlodka_compose.features.list.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.accompanist.glide.rememberGlidePainter
import dev.kissed.podlodka_compose.app.DI
import dev.kissed.podlodka_compose.R
import dev.kissed.podlodka_compose.app.App
import dev.kissed.podlodka_compose.data.MockSessionsRepository
import dev.kissed.podlodka_compose.features.list.SessionListFeature
import dev.kissed.podlodka_compose.features.list.SessionState
import dev.kissed.podlodka_compose.theming.AppColors

@Composable
fun SessionView(
  session: SessionState,
  onClick: () -> Unit,
  onBookmarkToggle: () -> Unit
) {

  val imagePainter = rememberGlidePainter(
    request = session.session.imageUrl,
    requestBuilder = {
      placeholder(R.drawable.notification_bg)
      transition(DrawableTransitionOptions.withCrossFade())
      centerCrop()
    }
  )

  SessionView(
    session = session,
    image = {
      Image(
        imagePainter,
        "Speaker ${session.session.speaker} avatar",
        Modifier
          .fillMaxSize()
          .clip(CircleShape)
      )
    },
    onClick = onClick,
    onBookmarkToggle = onBookmarkToggle
  )
}

@Composable
private fun SessionView(
  session: SessionState,
  image: @Composable BoxScope.() -> Unit,
  onClick: () -> Unit,
  onBookmarkToggle: () -> Unit,
) {
  Button(
    onClick,
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    shape = RoundedCornerShape(16.dp),
    elevation = ButtonDefaults.elevation(defaultElevation = 4.dp),
    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
    contentPadding = PaddingValues(8.dp)
  ) {
    Row {
      Box(Modifier.requiredSize(70.dp)) {
        image()
      }
      Spacer(Modifier.size(16.dp))
      Column(
        Modifier.weight(1f)
      ) {
        Text(
          session.session.speaker,
          Modifier.fillMaxWidth(),
          textAlign = TextAlign.Start,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
        Text(
          session.session.timeInterval,
          Modifier.fillMaxWidth(),
          textAlign = TextAlign.Start,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Text(
          session.session.description,
          Modifier.fillMaxWidth(),
          textAlign = TextAlign.Start,
          fontSize = 13.sp,
          fontWeight = FontWeight.Normal,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
        )
      }
      Spacer(Modifier.size(8.dp))
      IconToggleButton(
        checked = session.isBookmarked,
        onCheckedChange = { onBookmarkToggle() },
        Modifier
          .align(Alignment.CenterVertically)
          .padding(end = 8.dp)
          .size(30.dp)
      ) {
        Icon(
          Icons.Filled.Favorite,
          "Favorite",
          tint = if (session.isBookmarked) {
            AppColors.bookmarkChecked()
          } else {
            Color.Gray
          }
        )
      }
    }
  }
}

@Preview
@Composable
private fun SessionPreview() {
  SessionView(
    session =
    SessionState(
      session = MockSessionsRepository.getCachedSessions().random().copy(
        speaker = "Jake Wharton"
      ),
      isBookmarked = false
    ),
    image = {
      Icon(
        Icons.Filled.Dangerous,
        "",
        Modifier
          .fillMaxSize()
          .align(Alignment.Center)
          .clip(CircleShape)
      )
    },
    onClick = {},
    onBookmarkToggle = {}
  )
}