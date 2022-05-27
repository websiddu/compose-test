package tv.compose.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.tv.compose.foundation.PivotOffsets
import androidx.tv.compose.foundation.lazy.list.TvLazyRow
import androidx.tv.compose.foundation.lazy.list.items
import tv.compose.ui.components.TvCard
import tv.compose.ui.components.TvWideCard
import tv.compose.ui.focus.ChildFocusState
import tv.compose.ui.focus.restoreFocusParent



@Composable
fun CardsPage(navController: NavController) {
  Column(modifier = Modifier.padding(0.dp, 40.dp)) {
    
    val focusState = remember {
      ChildFocusState()
    }
    
    val focusState1 = remember {
      ChildFocusState()
    }
    
    
    TvLazyRow(
      horizontalArrangement = Arrangement.spacedBy(20.dp),
      contentPadding = PaddingValues(58.dp, 20.dp),
      modifier = Modifier.restoreFocusParent(focusState),
    ) {
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
    }
  
    TvLazyRow(
      horizontalArrangement = Arrangement.spacedBy(20.dp),
      contentPadding = PaddingValues(58.dp, 20.dp),
      modifier = Modifier.restoreFocusParent(focusState1)
    ) {
      item { TvWideCard(state = focusState1) {} }
      item { TvWideCard(state = focusState1) {} }
      item { TvWideCard(state = focusState1) {} }
    }
    
  }
}