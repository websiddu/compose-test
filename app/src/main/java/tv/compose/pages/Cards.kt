package tv.compose.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.tv.compose.foundation.PivotOffsets
import androidx.tv.compose.foundation.lazy.list.TvLazyColumn
import androidx.tv.compose.foundation.lazy.list.TvLazyRow
import androidx.tv.compose.foundation.lazy.list.items
import tv.compose.ui.components.TvCard
import tv.compose.ui.components.TvWideCard
import tv.compose.ui.focus.ChildFocusState
import tv.compose.ui.focus.restoreFocusParent


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardsPage(navController: NavController) {
  
  Column() {
  
  
    val focusState = remember { ChildFocusState() }
    val focusState1 = remember { ChildFocusState() }
    val focusState2 = remember { ChildFocusState() }
    val focusState3 = remember { ChildFocusState() }
    val focusState4 = remember { ChildFocusState() }
    
    TvLazyColumn(
      contentPadding = PaddingValues(0.dp, 20.dp),
      verticalArrangement = Arrangement.spacedBy(32.dp),
      modifier = Modifier.focusGroup()
    ) {
  
  
      item {
        TvLazyRow(
          horizontalArrangement = Arrangement.spacedBy(20.dp),
          contentPadding = PaddingValues(58.dp, 0.dp),
        ) {
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
        }
      }
  
      item {
        TvLazyRow(
          horizontalArrangement = Arrangement.spacedBy(20.dp),
          contentPadding = PaddingValues(58.dp, 0.dp),
        ) {
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
        }
      }
  
      item {
        TvLazyRow(
          horizontalArrangement = Arrangement.spacedBy(20.dp),
          contentPadding = PaddingValues(58.dp, 0.dp),
        ) {
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
        }
      }
  
      item {
        TvLazyRow(
          horizontalArrangement = Arrangement.spacedBy(20.dp),
          contentPadding = PaddingValues(58.dp, 0.dp),
        ) {
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
        }
      }
  
      item {
        TvLazyRow(
          horizontalArrangement = Arrangement.spacedBy(20.dp),
          contentPadding = PaddingValues(58.dp, 0.dp),
        ) {
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
        }
      }
  
      item {
        TvLazyRow(
          horizontalArrangement = Arrangement.spacedBy(20.dp),
          contentPadding = PaddingValues(58.dp, 0.dp),
        ) {
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
        }
      }
  
      item {
        TvLazyRow(
          horizontalArrangement = Arrangement.spacedBy(20.dp),
          contentPadding = PaddingValues(58.dp, 0.dp),
        ) {
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
          item { TvCard() {} }
        }
      }
      
      
//
//      item {
//        TvLazyRow(
//          horizontalArrangement = Arrangement.spacedBy(20.dp),
//          contentPadding = PaddingValues(58.dp, 20.dp),
//          modifier = Modifier.restoreFocusParent(focusState),
//        ) {
//          item { TvCard(state = focusState) {} }
//          item { TvCard(state = focusState) {} }
//          item { TvCard(state = focusState) {} }
//          item { TvCard(state = focusState) {} }
//          item { TvCard(state = focusState) {} }
//          item { TvCard(state = focusState) {} }
//          item { TvCard(state = focusState) {} }
//          item { TvCard(state = focusState) {} }
//          item { TvCard(state = focusState) {} }
//          item { TvCard(state = focusState) {} }
//          item { TvCard(state = focusState) {} }
//        }
//      }
//
//      item {
//        TvLazyRow(
//          horizontalArrangement = Arrangement.spacedBy(20.dp),
//          contentPadding = PaddingValues(58.dp, 20.dp),
//          modifier = Modifier.restoreFocusParent(focusState2),
//        ) {
//          item { TvCard(state = focusState2) {} }
//          item { TvCard(state = focusState2) {} }
//          item { TvCard(state = focusState2) {} }
//          item { TvCard(state = focusState2) {} }
//          item { TvCard(state = focusState2) {} }
//          item { TvCard(state = focusState2) {} }
//          item { TvCard(state = focusState2) {} }
//          item { TvCard(state = focusState2) {} }
//          item { TvCard(state = focusState2) {} }
//          item { TvCard(state = focusState2) {} }
//          item { TvCard(state = focusState2) {} }
//        }
//      }
//
//      item {
//        TvLazyRow(
//          horizontalArrangement = Arrangement.spacedBy(20.dp),
//          contentPadding = PaddingValues(58.dp, 20.dp),
//          modifier = Modifier.restoreFocusParent(focusState3)
//        ) {
//          item { TvWideCard(state = focusState3) {} }
//          item { TvWideCard(state = focusState3) {} }
//          item { TvWideCard(state = focusState3) {} }
//        }
//      }
//
//      item {
//        TvLazyRow(
//          horizontalArrangement = Arrangement.spacedBy(20.dp),
//          contentPadding = PaddingValues(58.dp, 20.dp),
//          modifier = Modifier.restoreFocusParent(focusState1)
//        ) {
//          item { TvWideCard(state = focusState1) {} }
//          item { TvWideCard(state = focusState1) {} }
//          item { TvWideCard(state = focusState1) {} }
//        }
//      }
//
//
//      item {
//        TvLazyRow(
//          horizontalArrangement = Arrangement.spacedBy(20.dp),
//          contentPadding = PaddingValues(58.dp, 20.dp),
//          modifier = Modifier.restoreFocusParent(focusState4)
//        ) {
//          item { TvWideCard(state = focusState4) {} }
//          item { TvWideCard(state = focusState4) {} }
//          item { TvWideCard(state = focusState4) {} }
//          item { TvWideCard(state = focusState4) {} }
//          item { TvWideCard(state = focusState4) {} }
//        }
//      }
    }
    
  }
  
}

