package tv.compose.ui.components


import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TVDrawer(
  modifier: Modifier = Modifier,
  tvDrawerState: MutableState<TVDrawerState> = mutableStateOf(TVDrawerState.CLOSE),
  content: @Composable BoxScope.(TVDrawerState) -> Unit
) {
  val focusManager = LocalFocusManager.current
  val internalModifier =
    modifier
      .fillMaxHeight()
      .onFocusChanged {
        if (it.isFocused) {
          tvDrawerState.value = TVDrawerState.OPEN
          focusManager.moveFocus(FocusDirection.In)
        } else if (!it.hasFocus && !it.isFocused) {
          tvDrawerState.value = TVDrawerState.CLOSE
        }
      }
      .focusable()
  
  Box(modifier = internalModifier) {
    content(tvDrawerState.value)
  }
}

enum class TVDrawerState {
  OPEN, CLOSE
}