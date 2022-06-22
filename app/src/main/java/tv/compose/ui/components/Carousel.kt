package tv.compose.ui.components

import android.os.Handler
import android.os.Looper
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Carousel(
  modifier: Modifier = Modifier,
  enterTransition: EnterTransition = fadeIn(animationSpec = tween(900)),
  exitTransition: ExitTransition = fadeOut(animationSpec = tween(900)),
  frameIndexToDisplay: MutableState<Int> = mutableStateOf(0),
  timeBetweenSlides: Long = 2500,
  pageChangeCallback: (Int) -> Unit = {},
  fcContent: FCScope.() -> Unit
) {
  val frames = FCScope().apply(fcContent).getCollection().toList()
  val pageCount = frames.size
  
  if (frames.isEmpty()) {
    return
  }
  
  val content: PagerScope<Int>.() -> Unit = {
    for (i in 1..frames.size) {
      frame(i - 1, frames[i - 1])
    }
  }
  
  /** Listen to page scroll events */
  LaunchedEffect(Unit) {
    snapshotFlow { frameIndexToDisplay.value }.collect { page ->
      pageChangeCallback(page)
    }
  }
  
  /** Automatically scroll the pages */
  val scope = rememberCoroutineScope()
  val job: MutableState<Job?> = remember { mutableStateOf(null) }
  
  LaunchedEffect(Unit) {
    launchScrollJob(job, scope, timeBetweenSlides, frameIndexToDisplay, pageCount)
  }
  
  val focusManager = LocalFocusManager.current
  val pagerFocusRequester = FocusRequester()
  val shouldDisplayLRButtons = remember { mutableStateOf(false) }
  
  Box(modifier = modifier
    .onFocusChanged { focusState ->
      if (focusState.isFocused) {
        job.value?.cancel()
        focusManager.moveFocus(FocusDirection.In)
        shouldDisplayLRButtons.value = true
      } else {
        if (!focusState.hasFocus) {
          shouldDisplayLRButtons.value = false
          launchScrollJob(job, scope, timeBetweenSlides, frameIndexToDisplay, pageCount)
        }
      }
    }
    .focusable()) {
    
    Row {
      
      NavigationBox(
        Modifier/*.align(Alignment.CenterStart)*/,
        shouldDisplayLRButtons,
        frameIndexToDisplay,
        pageCount,
        -1,
        pagerFocusRequester
      )
      
      Box {
        Pager<Int>(modifier = Modifier
          .fillMaxWidth(0.99f)
          .onKeyEvent {
            if (Key.Back == it.key) {
              focusManager.clearFocus()
            }
            false
          }
          .focusRequester(pagerFocusRequester)
          .onFocusChanged { focusState ->
            if (focusState.isFocused) {
              focusManager.moveFocus(FocusDirection.In)
            }
          }
          .focusable(),
          enterTransition,
          exitTransition,
          frameIndexToDisplay,
          content)
        
        PaginationIndicator(
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(0.dp, 0.dp, 32.dp, 32.dp),
          pageCount = frames.size,
          frameIndexToDisplay = frameIndexToDisplay,
          activeColor = Color.White,
          inactiveColor = Color.White.copy(alpha = 0.5f)
        )
      }
      
      NavigationBox(
        Modifier/*.align(Alignment.CenterEnd)*/,
        shouldDisplayLRButtons,
        frameIndexToDisplay,
        pageCount,
        1,
        pagerFocusRequester
      )
    }
  }
}

@Composable
fun NavigationBox(
  modifier: Modifier,
  shouldDisplay: MutableState<Boolean>,
  frameIndexToDisplay: MutableState<Int>,
  pageCount: Int,
  scrollBy: Int,
  fr: FocusRequester
) {
  
  
  if (!shouldDisplay.value) {
    return
  }
  
  Box(modifier = modifier
    .zIndex(2.0f)
    .width(1.dp)
    .fillMaxHeight()
    //.border(2.dp, Color.Black)
    .onFocusChanged { focusState ->
      if (focusState.isFocused) {
        scroll(frameIndexToDisplay, scrollBy, pageCount)
        //TODO : wait for scroll to complete   !!!  HACK !!!
        Handler(Looper.getMainLooper()).postDelayed(
          {
            // This method will be executed once the timer is over
            fr.requestFocus()
          },
          500 // value in milliseconds
        )
        fr.requestFocus()
      }
    }
    .focusable()
  )
}

fun scroll(frameIndexToDisplay: MutableState<Int>, scrollBy: Int, pageCount: Int) {
  frameIndexToDisplay.value = Math.floorMod(frameIndexToDisplay.value + scrollBy, pageCount)
}

fun launchScrollJob(
  job: MutableState<Job?>,
  scope: CoroutineScope,
  timeBetweenSlides: Long,
  frameIndexToDisplay: MutableState<Int>,
  pageCount: Int
) {
  if (job.value?.isActive == true) {
    return
  }
  
  job.value = scope.launch {
    while (true) {
      yield()
      delay(timeBetweenSlides)
      scroll(frameIndexToDisplay, 1, pageCount)
    }
  }
}