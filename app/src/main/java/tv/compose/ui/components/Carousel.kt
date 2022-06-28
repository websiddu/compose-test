package tv.compose.ui.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

/**
 * Composes a hero card rotator to highlight a piece of content. It’s generally displayed on the
 * landing pages.
 *
 * [carouselState] contains the current page that is displayed.
 *
 * After every [timeBetweenSlides] the current page exits the screen using [exitTransition] and the
 * next page appears using [enterTransition].
 * The carousel stops rotating when focus is shifted into it. And it resumes rotation when focus is
 * shifted out of it.
 *
 * [pagerIndicator] can be used to show the position of the
 * current page among other pages.
 *
 * All the pages to be displayed in the carousel can be passed as [content].
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Carousel(
    carouselState: CarouselState,
    modifier: Modifier = Modifier,
    enterTransition: EnterTransition = fadeIn(animationSpec = tween(900)),
    exitTransition: ExitTransition = fadeOut(animationSpec = tween(900)),
    timeBetweenSlides: Long = 2500L,
    pagerIndicator: @Composable (PagerState<Int>, Int) -> Unit = { pagerState: PagerState<Int>, pageCount: Int ->
        PagerIndicator(pagerState = pagerState, pageCount = pageCount)
    },
    content: CarouselScope.() -> Unit
) {
    val frames = remember {
        arrayListOf<@Composable () -> Unit>()
    }
    frames.addAll(CarouselScope().apply(content).getCollection())

    if (frames.isEmpty()) {
        return
    }

    val pagerContent: PagerScope<Int>.() -> Unit = {
        frames.forEachIndexed { index, _ -> frame(index, frames[index]) }
    }

    val scope = rememberCoroutineScope()
    val job: MutableState<Job?> = remember { mutableStateOf(null) }
    val focusManager = LocalFocusManager.current

    Box {

        Pager(modifier = modifier
            .onKeyEvent {
                if (Key.Back == it.key) {
                    focusManager.clearFocus()
                }
                false
            }
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    carouselState.isFocused = true
                    job.value?.cancel()
                    focusManager.moveFocus(FocusDirection.In)
                } else {
                    if (!focusState.hasFocus) {
                        carouselState.isFocused = false
                        launchScrollJob(
                            job,
                            scope,
                            timeBetweenSlides,
                            carouselState.pagerState.currentPage,
                            frames.size
                        )
                    }
                }
            }
            .focusable(),
            enterTransition,
            exitTransition,
            carouselState.pagerState,
            pagerContent)

        pagerIndicator.invoke(carouselState.pagerState, frames.size)
    }
}

fun scroll(currentPage: MutableState<Int>, scrollBy: Int, pageCount: Int) {
    currentPage.value = Math.floorMod(currentPage.value + scrollBy, pageCount)
}

fun launchScrollJob(
    job: MutableState<Job?>,
    scope: CoroutineScope,
    timeBetweenSlides: Long,
    currentPage: MutableState<Int>,
    pageCount: Int
) {
    if (job.value?.isActive == true) {
        return
    }

    job.value = scope.launch {
        while (true) {
            yield()
            delay(timeBetweenSlides)
            scroll(currentPage, 1, pageCount)
        }
    }
}

class CarouselState(val pagerState: PagerState<Int>, var isFocused: Boolean = false)