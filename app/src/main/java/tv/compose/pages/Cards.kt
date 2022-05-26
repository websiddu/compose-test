package tv.compose.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tv.compose.ui.components.TvCard
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
    
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(20.dp),
      contentPadding = PaddingValues(58.dp, 20.dp),
      modifier = Modifier.restoreFocusParent(focusState)
    ) {
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
      item { TvCard(state = focusState) {} }
    }
    
    
    
    
    
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(20.dp),
      contentPadding = PaddingValues(58.dp, 20.dp),
      modifier = Modifier.restoreFocusParent(focusState1)
    ) {
      item { TvCard(state = focusState1) {} }
      item { TvCard(state = focusState1) {} }
      item { TvCard(state = focusState1) {} }
      item { TvCard(state = focusState1) {} }
      item { TvCard(state = focusState1) {} }
      item { TvCard(state = focusState1) {} }
    }
    
  }
}