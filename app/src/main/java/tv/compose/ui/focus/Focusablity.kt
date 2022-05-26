package tv.compose.ui.focus

import androidx.compose.foundation.focusable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged


class ChildFocusState {
  internal var previouslyFocusedItem: FocusRequester? by mutableStateOf(null)
}

fun Modifier.restoreFocusParent(state: ChildFocusState): Modifier = composed {
  var previousHasFocus by remember { mutableStateOf(false) }
  Modifier
    .onFocusChanged {
      if (it.isFocused && !previousHasFocus) state.previouslyFocusedItem?.requestFocus()
      previousHasFocus = it.hasFocus
    }
    .then(state.previouslyFocusedItem?.let { Modifier.focusable() } ?: Modifier)
}

fun Modifier.restoreFocusChild(state: ChildFocusState): Modifier = composed {
  val focusRequester = remember { FocusRequester() }
  Modifier
    .onFocusChanged { if (it.isFocused) state.previouslyFocusedItem = focusRequester }
    .focusRequester(focusRequester)
}