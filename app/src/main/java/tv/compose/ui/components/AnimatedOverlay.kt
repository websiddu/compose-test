package tv.compose.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay


@Composable
fun AnimatedOverlay(
  modifier: Modifier = Modifier,
  delayInMillis: Long = 1500,
  enterTransition: EnterTransition = slideInHorizontally(initialOffsetX = { it * 4 }),
  exitTransition: ExitTransition = slideOutHorizontally(),
  backgroundComposable: @Composable BoxScope.() -> Unit,
  overlayComposable: @Composable BoxScope.() -> Unit
) {
  Box(modifier = modifier) {
    val boxScope = this
    backgroundComposable.invoke(boxScope)
//    overlayComposable.invoke(boxScope)
    
    val visible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(delayInMillis)
        visible.value = true
    }

    AnimatedVisibility(
        modifier = Modifier.align(Alignment.BottomStart),
        visible = visible.value,
        enter = enterTransition,
        exit = exitTransition
    ) {
        overlayComposable.invoke(boxScope)
    }
  }
}