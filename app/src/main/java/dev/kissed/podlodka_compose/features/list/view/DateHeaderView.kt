package dev.kissed.podlodka_compose.features.list.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DateHeaderView(date: String) {
  Text(
    date,
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    textAlign = TextAlign.Start,
    fontSize = 15.sp,
    fontWeight = FontWeight.Normal,
  )
}

@Preview
@Composable
private fun DateHeaderPreview() {
  DateHeaderView(date = "19 апреля")
}