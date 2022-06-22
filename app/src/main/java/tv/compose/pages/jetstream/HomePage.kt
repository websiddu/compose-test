package tv.compose.pages.jetstream

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.tv.compose.foundation.lazy.list.TvLazyColumn
import tv.compose.pages.SlideFade
import tv.compose.ui.focus.ChildFocusState
import tv.compose.ui.focus.FocusableSurface
import tv.compose.ui.focus.SurfaceStyle
import tv.compose.ui.focus.restoreFocusParent
import tv.compose.ui.vm.TmdbViewModel

@Composable
fun NavTab(
  label: String,
  onFocus: () -> Unit,
  state: ChildFocusState? = null,
  selected: Boolean = false,
) {
  
  val style = if (!selected) SurfaceStyle(
    backgroundColor = Color.Unspecified,
    contentColor = MaterialTheme.colorScheme.onBackground
  ) else SurfaceStyle(
    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
  )
  
  FocusableSurface(
    onFocus = onFocus,
    modifier = Modifier.padding(16.dp, 8.dp),
    shape = CircleShape,
    style = style,
    state = state,
    focusStyle = SurfaceStyle(
      scale = 1.05f,
      shadowElevation = 8.dp
    )
  ) {
    Text(text = label, style = MaterialTheme.typography.labelMedium)
  }
}

@Composable
fun TopNav(
  tabs: List<String> = listOf(),
  currentTab: MutableState<Int>,
  previousTab: MutableState<Int>
) {
  val focusSate = remember { ChildFocusState() }
  
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .padding(58.dp, 0.dp)
  ) {
    Spacer(modifier = Modifier.size(20.dp))
    
    Row(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier
        .padding(0.dp, 24.dp)
        .restoreFocusParent(focusSate)
    ) {
      tabs.forEachIndexed { index, label ->
        NavTab(
          label = label,
          selected = currentTab.value == index,
          state = focusSate,
          onFocus = {
            previousTab.value = currentTab.value
            currentTab.value = index
          }
        )
      }
    }
    Spacer(modifier = Modifier.size(20.dp))
  }
}

@Composable
fun HomePage(navController: NavController, viewModel: TmdbViewModel) {
  val currentTab = remember { mutableStateOf(0) }
  val previousTab = remember { mutableStateOf(0) }
  val tabs = listOf<String>("Home", "Movies", "Shows", "Live");
  
  TvLazyColumn(modifier = Modifier.fillMaxSize()) {
    item {
      TopNav(tabs, currentTab, previousTab)
    }
    item {
      SlideFade(tabs, currentTab.value, previousTab.value, viewModel, navController)
    }
  }
}