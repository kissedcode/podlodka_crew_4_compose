package dev.kissed.podlodka_compose.features.list.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kissed.podlodka_compose.models.Session

@Composable
fun BookmarksView(bookmarks: List<Session>, onBookmarkClick: (id: String) -> Unit) {
  LazyRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
  ) {
    items(bookmarks) { session ->
      BookmarkView(
        session = session,
        onClick = { onBookmarkClick(session.id) }
      )
    }
  }
}

@Composable
private fun BookmarkView(session: Session, onClick: () -> Unit) {
  Button(
    onClick,
    Modifier
      .size(130.dp),
    shape = RoundedCornerShape(16.dp),
    elevation = ButtonDefaults.elevation(defaultElevation = 4.dp),
    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
    contentPadding = PaddingValues(8.dp)
  ) {
    Column(
      Modifier.align(Alignment.Top)
    ) {
      TextItemView(
        session.timeInterval,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
      )
      TextItemView(
        session.date,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        maxLines = 1,
      )
      Spacer(Modifier.size(8.dp))
      TextItemView(
        session.speaker,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
      )
      TextItemView(
        session.description,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        maxLines = 3,
      )
    }
  }
}

@Preview
@Composable
private fun BookmarkPreview() {
  BookmarkView(
    session = Session(
      id = "1",
      speaker = "Жека Вартанов",
      date = "19 апреля",
      timeInterval = "10.00-11.00",
      description = "Крутой доклад Крутой доклад Крутой доклад Крутой доклад !",
      imageUrl = "ya.ru"
    ),
    onClick = {}
  )
}

@Composable
private fun TextItemView(text: String, fontSize: TextUnit, maxLines: Int, fontWeight: FontWeight) {
  Text(
    text,
    Modifier.fillMaxWidth(),
    textAlign = TextAlign.Start,
    fontSize = fontSize,
    fontWeight = fontWeight,
    maxLines = maxLines,
    overflow = TextOverflow.Ellipsis,
  )
}

@Preview
@Composable
private fun TextItemPreview() {
  TextItemView(
    text = "Привет как дела",
    fontSize = 13.sp,
    maxLines = 1,
    fontWeight = FontWeight.Bold
  )
}

