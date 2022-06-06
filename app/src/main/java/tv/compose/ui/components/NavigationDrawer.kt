package tv.compose.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged

@Composable
fun TVNavigationDrawer(
  modifier: Modifier = Modifier,
  navigationDrawerState: NavigationDrawerState,
  rtl: Boolean = false,
  header: NavigationDrawerItem = NavigationDrawerItem(),
  footer: NavigationDrawerItem = NavigationDrawerItem(),
  drawerContent: NavigationDrawerScope.() -> Unit
) {
  TVDrawer(
    modifier = modifier.animateContentSize(),
    isDrawerOpen = navigationDrawerState.isDrawerActive,
    openedState = {
      Column(
        modifier = modifier
          .animateContentSize()
          .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
      ) {
        Row {
          header.openedState.invoke(this)
        }
        Row {
          Column {
            LoadDrawerItems(
              rtl = rtl,
              navigationDrawerState = navigationDrawerState,
              drawerContent = drawerContent
            )
          }
        }
        Row {
          footer.openedState.invoke(this)
        }
      }
    },
    closedState = {
      Column(
        modifier = modifier
          .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
      ) {
        Row {
          header.closedState.invoke(this)
        }
        Row {
          Column {
            LoadDrawerItems(
              rtl = rtl,
              navigationDrawerState = navigationDrawerState,
              drawerContent = drawerContent,
            )
          }
        }
        Row {
          footer.closedState.invoke(this)
        }
      }
    }
  )
}

@Composable
fun LoadDrawerItems(
  rtl: Boolean,
  navigationDrawerState: NavigationDrawerState,
  drawerContent: NavigationDrawerScope.() -> Unit
) {
  val navigationDrawerScope = remember { NavigationDrawerScope().apply(drawerContent) }
  val drawerItems: List<NavigationDrawerItem> = navigationDrawerScope.getCollection().toList()
  
  if (navigationDrawerState.isDrawerActive.value) {
    val focusRequesters: MutableList<FocusRequester> = mutableListOf()
    for (i in 0..drawerItems.lastIndex) {
      focusRequesters.add(remember { FocusRequester() })
    }
    LaunchedEffect(key1 = Unit) {
      focusRequesters[navigationDrawerState.focusedIndex.value].requestFocus()
    }
    for (i in 0..drawerItems.lastIndex) {
      Row(modifier = drawerItems[i].onFocusModifier
        .focusRequester(focusRequesters[i])
        .onFocusChanged {
          if (it.isFocused) {
            navigationDrawerState.focusedIndex.value = i
          }
        }
        .focusProperties {
          down =
            if (i == drawerItems.lastIndex) focusRequesters[i]
            else FocusRequester.Default
          up =
            if (i == 0) focusRequesters[i] else FocusRequester.Default
          left = if (rtl) FocusRequester.Default else focusRequesters[i]
          right = if (rtl) focusRequesters[i] else FocusRequester.Default
        }
        .clickable(onClick = { // accept a lambda
          drawerItems[i].onClick.invoke()
          navigationDrawerState.isDrawerActive.value = false
        })
      ) {
        drawerItems[i].openedState.invoke(this)
      }
    }
  } else {
    for (i in 0..drawerItems.lastIndex) {
      Row {
        drawerItems[i].closedState.invoke(this)
      }
    }
  }
}

data class NavigationDrawerItem(
  var closedState: @Composable (RowScope.() -> Unit) = {},
  var openedState: @Composable (RowScope.() -> Unit) = {},
  var onFocusModifier: Modifier = Modifier,
  var onClick: () -> Unit = {}
)

data class NavigationDrawerState(
  val isDrawerActive: MutableState<Boolean> = mutableStateOf(false),
  val focusedIndex: MutableState<Int> = mutableStateOf(0)
)

@Composable
fun rememberNavigationDrawerState(
  isDrawerActive: MutableState<Boolean> = mutableStateOf(false),
  focusedIndex: MutableState<Int> = mutableStateOf(0)
) =
  remember(isDrawerActive.value) {
    NavigationDrawerState(isDrawerActive, focusedIndex)
  }

class NavigationDrawerScope : ListOfScope<NavigationDrawerItem>()

abstract class ListOfScope<T>(
  private val internalCollection: MutableCollection<T> = mutableListOf()
) {
  
  fun item(item: T) {
    internalCollection.add(item)
  }
  
  fun getCollection(): Collection<T> = internalCollection
}