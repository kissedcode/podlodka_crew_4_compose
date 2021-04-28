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
fun HeaderView(text: String) {
  Text(
    text,
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .padding(top = 24.dp)
    ,
    textAlign = TextAlign.Start,
    fontSize = 17.sp,
    fontWeight = FontWeight.Bold,
  )
}

@Preview
@Composable
private fun HeaderPreview() {
  HeaderView(text = "Сессии")
}