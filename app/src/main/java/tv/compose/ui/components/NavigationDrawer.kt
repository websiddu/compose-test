package tv.compose.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp


@Composable
fun TVNavigationDrawer(
  modifier: Modifier = Modifier,
  navigationDrawerState: NavigationDrawerState = rememberNavigationDrawerState(),
  dockTo: TVNavDockingDirection = TVNavDockingDirection.LEFT,
  header: @Composable RowScope.(TVDrawerState) -> Unit = {},
  footer: @Composable RowScope.(TVDrawerState) -> Unit = {},
  drawerContent: NavigationDrawerScope.() -> Unit
) {
  TVDrawer(
    tvDrawerState = navigationDrawerState.drawerState,
    content = {
      Column(
        modifier = modifier
          .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          FocusGuardRow(
            navigationDrawerState = navigationDrawerState,
            focusDirection = FocusDirection.Down
          )
          Row {
            header.invoke(this, navigationDrawerState.drawerState.value)
          }
        }
        Column {
          LoadDrawerItems(
            dockTo = dockTo,
            navigationDrawerState = navigationDrawerState,
            drawerContent = drawerContent
          )
        }
        Column {
          Row {
            footer.invoke(this, navigationDrawerState.drawerState.value)
          }
          FocusGuardRow(
            navigationDrawerState = navigationDrawerState,
            focusDirection = FocusDirection.Up
          )
        }
      }
    }
  )
}

@Composable
internal fun LoadDrawerItems(
  dockTo: TVNavDockingDirection,
  navigationDrawerState: NavigationDrawerState,
  drawerContent: NavigationDrawerScope.() -> Unit
) {
  val navigationDrawerScope = remember { NavigationDrawerScope().apply(drawerContent) }
  val drawerItems: List<@Composable RowScope.(TVDrawerState) -> Unit> =
    navigationDrawerScope.getCollection().toList()
  if (navigationDrawerState.drawerState.value == TVDrawerState.OPEN) {
    val focusRequesters: List<FocusRequester> = buildList {
      for (i in 0..drawerItems.lastIndex) {
        add(remember { FocusRequester() })
      }
    }
    LaunchedEffect(key1 = Unit) {
      focusRequesters[navigationDrawerState.focusedIndex.value].requestFocus()
    }
    for (i in 0..drawerItems.lastIndex) {
      Row(modifier = Modifier
        .focusRequester(focusRequesters[i])
        .onFocusChanged {
          if (it.hasFocus) {
            navigationDrawerState.focusedIndex.value = i
          }
        }
        .focusProperties {
          left =
            if (dockTo == TVNavDockingDirection.RIGHT)
              FocusRequester.Default
            else focusRequesters[i]
          right =
            if (dockTo == TVNavDockingDirection.LEFT)
              FocusRequester.Default
            else focusRequesters[i]
        }
      ) {
        drawerItems[i].invoke(this, TVDrawerState.OPEN)
      }
    }
  } else {
    drawerItems.forEach {
      Row {
        it.invoke(this, TVDrawerState.CLOSE)
      }
    }
  }
}

@Composable
internal fun FocusGuardRow(
  navigationDrawerState: NavigationDrawerState,
  focusDirection: FocusDirection
) {
  val focusManager = LocalFocusManager.current
  Row(modifier = Modifier
    .height(1.dp)
    .width(1.dp)
    .focusProperties {
      canFocus = navigationDrawerState.drawerState.value == TVDrawerState.OPEN
    }
    .onFocusChanged {
      if (it.isFocused) {
        focusManager.moveFocus(focusDirection = focusDirection)
      }
    }
    .focusable()) {
  }
}

class NavigationDrawerState(
  val drawerState: MutableState<TVDrawerState> = mutableStateOf(TVDrawerState.CLOSE),
  val focusedIndex: MutableState<Int> = mutableStateOf(0)
)

@Composable
fun rememberNavigationDrawerState(
  isDrawerActive: MutableState<TVDrawerState> = mutableStateOf(TVDrawerState.CLOSE),
  focusedIndex: MutableState<Int> = mutableStateOf(0)
) =
  remember(isDrawerActive.value) {
    NavigationDrawerState(isDrawerActive, focusedIndex)
  }

class NavigationDrawerScope : ListOfScope<@Composable RowScope.(TVDrawerState) -> Unit>()

abstract class ListOfScope<T>(
  private val internalCollection: MutableCollection<T> = mutableListOf()
) {
  fun item(item: T) {
    internalCollection.add(item)
  }
  
  fun getCollection(): Collection<T> = internalCollection
}

enum class TVNavDockingDirection {
  LEFT, RIGHT
}