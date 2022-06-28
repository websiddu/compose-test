package tv.compose.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun FrameWithAnimatedOverlay(
    carouselState: CarouselState,
    modifier: Modifier = Modifier,
    delayInMillis: Long = 1500,
    enterTransition: EnterTransition = slideInHorizontally(initialOffsetX = { it * 4 }),
    exitTransition: ExitTransition = slideOutHorizontally(),
    backgroundComposable: @Composable BoxScope.() -> Unit,
    overlayComposable: @Composable BoxScope.() -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val visibleState = remember { MutableTransitionState(initialState = false) }

    LaunchedEffect(focusRequester, visibleState) {
        snapshotFlow { visibleState.isIdle }
            .distinctUntilChanged()
            .collect { animationFinished ->
                if (animationFinished && visibleState.currentState && carouselState.isFocused) {
                    focusRequester.requestFocus()
                }
            }
    }

    Box(modifier = modifier) {
        val boxScope = this
        backgroundComposable.invoke(boxScope)

        LaunchedEffect(Unit) {
            delay(delayInMillis)
            visibleState.targetState = true
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .focusRequester(focusRequester),
            visibleState = visibleState,
            enter = enterTransition,
            exit = exitTransition
        ) {
            overlayComposable.invoke(boxScope)
        }
    }
}