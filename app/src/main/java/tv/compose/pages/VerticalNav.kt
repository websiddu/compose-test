package tv.compose.pages

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import tv.compose.ui.components.*
import tv.compose.ui.focus.FocusableSurface
import tv.compose.ui.focus.SurfaceStyle

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun VerticalNavPage(navController: NavController) {
  
  val fr = FocusRequester()
  var hasFocus by remember { mutableStateOf(false) }
  val width = animateDpAsState(targetValue = if (hasFocus) 220.dp else 48.dp)
  
  Row(
    modifier = Modifier
      .fillMaxSize()
  ) {
    Column(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.surfaceTint.copy(0.04f))
        .fillMaxHeight()
        .padding(12.dp, 20.dp)
        .width(width.value)
        .onFocusChanged {
          hasFocus = it.hasFocus
        }
        .focusRequester(fr),
      verticalArrangement = Arrangement.SpaceBetween,
    ) {
      Column() {}
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ListItem("Home", icon = Icons.Outlined.Home)
        ListItem("Movies", icon = Icons.Outlined.Movie)
        ListItem("Shows", icon = Icons.Outlined.Slideshow)
        ListItem("Trending", icon = Icons.Outlined.Whatshot)
        ListItem("Live", icon = Icons.Outlined.Sensors)
        ListItem("Library", icon = Icons.Outlined.VideoLibrary)
      }
      Column {
        ListItem("Settings", icon = Icons.Outlined.Settings)
      }
    }
    
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
    ) {
      TvButton(onPress = {}) {
        Text(text = "Test button")
      }
      Spacer(modifier = Modifier.height(40.dp))
      TvButton(onPress = {}) {
        Text(text = "Another button")
      }
    }
  }
  
}



