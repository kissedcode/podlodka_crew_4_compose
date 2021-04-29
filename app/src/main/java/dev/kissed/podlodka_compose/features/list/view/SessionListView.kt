package dev.kissed.podlodka_compose.features.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kissed.podlodka_compose.app.DI
import dev.kissed.podlodka_compose.features.list.SessionListFeature.State
import dev.kissed.podlodka_compose.features.list.view.BookmarksView
import dev.kissed.podlodka_compose.features.list.view.DateHeaderView
import dev.kissed.podlodka_compose.features.list.view.HeaderView
import dev.kissed.podlodka_compose.theming.AppColors
import kotlinx.coroutines.flow.collect

@Composable
fun SessionListView() {
  val feature = DI.sessionListFeature
  val state by feature.stateFlow.collectAsState()

  val scaffoldState = rememberScaffoldState()
  LaunchedEffect(Unit) {
    feature.news.collect {
      when (it) {
        SessionListFeature.News.TooManyBookmarks -> {
          scaffoldState.snackbarHostState.showSnackbar(
            "Не удалось добавить сессию в избранное"
          )
        }
      }
    }
  }

  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(
        backgroundColor = AppColors.surface(),
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
      ) {
        TextField(
          value = state.searchQuery,
          onValueChange = {
            feature.search(it)
          },
          Modifier
            .fillMaxWidth(),
          singleLine = true,
          leadingIcon = {
            Icon(Icons.Outlined.Search, contentDescription = "search")
          },
          trailingIcon = {
            if (state.searchQuery.isNotEmpty()) {
              IconButton(onClick = { feature.search("") }) {
                Icon(Icons.Filled.Close, contentDescription = "clear search query")
              }
            }
          },
          placeholder = {
            Text("Поиск")
          },
          colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = AppColors.surface(),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
          )
        )
      }
    }
  ) {
    SessionListView(
      state,
      onSessionClick = feature::sessionChoose,
      onBookmarkToggle = feature::toggleBookmark
    )
  }
}

@Composable
private fun SessionListView(
  state: State,
  onSessionClick: (id: String) -> Unit,
  onBookmarkToggle: (id: String) -> Unit
) {
  LazyColumn(
    Modifier
      .fillMaxSize()
      .background(AppColors.surface()),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(bottom = 16.dp),
  ) {
    if (state.bookmarks.isNotEmpty()) {
      item {
        HeaderView(text = "Избранное")
        Spacer(Modifier.size(16.dp))
        BookmarksView(
          bookmarks = state.bookmarks,
          onBookmarkClick = onSessionClick
        )
      }
    }
    item {
      HeaderView(text = "Сессии")
    }
    items(state.sessionGroups) { group ->
      DateHeaderView(date = group.date)
      group.sessions.forEach { session ->
        Spacer(Modifier.size(16.dp))
        SessionView(
          session,
          { onSessionClick(session.session.id) },
          { onBookmarkToggle(session.session.id) }
        )
      }
    }
  }
}

@Preview
@Composable
private fun ListViewPreview() {
  SessionListView(
    State(
      sessions = DI.mockSessionsRepository.getAllSessions(),
      bookmarkIds = setOf("1", "3"),
      searchQuery = ""
    ),
    onSessionClick = {},
    onBookmarkToggle = {}
  )
}