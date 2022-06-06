package tv.compose.ui.components


import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TVDrawer(
  modifier: Modifier = Modifier,
  isDrawerOpen: MutableState<Boolean>,
  openedState: @Composable BoxScope.() -> Unit,
  closedState: @Composable BoxScope.() -> Unit
) {
  
  val focusManager = LocalFocusManager.current
  val internalModifier =
    modifier
      .fillMaxHeight()
      .onFocusChanged {
        if (it.isFocused) {
          isDrawerOpen.value = true
          focusManager.moveFocus(FocusDirection.In)
        } else if (!it.hasFocus && !it.isFocused) {
          isDrawerOpen.value = false
        }
      }
      .focusable()
  
  Box(modifier = internalModifier) {
    if (isDrawerOpen.value) {
      openedState.invoke(this)
    } else {
      closedState.invoke(this)
    }
  }
}