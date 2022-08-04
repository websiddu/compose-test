package tv.compose.ui.focus

import android.view.animation.PathInterpolator
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusDirection.Companion.In
import androidx.compose.ui.focus.FocusDirection.Companion.Out
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalFocusManager


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

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
fun Modifier.tvFocusGroup() = composed {
  val focusManager = LocalFocusManager.current

  Modifier
    .onFocusChanged {
      if (it.isFocused)
        focusManager.moveFocus( if (it.hasFocus) Out else In )
    }
    .focusGroup()
}
