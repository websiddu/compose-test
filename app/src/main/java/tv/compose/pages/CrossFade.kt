package tv.compose.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.imageLoader
import coil.request.ImageRequest
import com.skydoves.landscapist.coil.CoilImage
import tv.compose.data.model.Movie
import tv.compose.ui.focus.FocusableSurface
import tv.compose.ui.focus.SurfaceStyle
import tv.compose.ui.vm.Loading
import tv.compose.ui.vm.Success
import tv.compose.ui.vm.Error
import tv.compose.ui.vm.TmdbViewModel
import tv.compose.ui.vm.UiState
import tv.compose.utils.Constants

@Composable
fun CrossFadeTest(
    navController: NavController,
    viewModel: TmdbViewModel
) {

    val context = LocalContext.current


    val moviesState: UiState<List<Movie>> by viewModel.getMovies()
        .observeAsState(initial = Loading())

    var currentItem by remember { mutableStateOf(0) }
    var previousItem by remember { mutableStateOf(-1) }

    val animVisibleState = remember { MutableTransitionState(false) }
        .apply { targetState = true }

    when (moviesState) {
        is Error -> {}
        is Loading -> {}
        is Success -> {
            val data = (moviesState as Success).data

            LaunchedEffect(Unit) {
                data.forEachIndexed { index, movie ->
                    val request = ImageRequest.Builder(context)
                        .data(Constants.URL.BACKDROP_URL + movie.backdrop)
                        .build()
                    context.imageLoader.enqueue(request)
                }
            }

            FocusableSurface(
                focusStyle = SurfaceStyle(
                    scale = 1f
                ),
                onDpadLeft = {
                    previousItem = currentItem

                    if (currentItem == 0) {
                        currentItem = data.count() - 1
                    } else {
                        currentItem -= 1
                    }

                },
                onDpadRight = {
                    previousItem = currentItem
                    if (currentItem == data.count() - 1) {
                        currentItem = 0
                    } else {
                        currentItem += 1
                    }
                },
            ) {
                val time = 3000

                data.forEachIndexed { index, movie ->
                    AnimatedVisibility(
                        visible = index == currentItem,
                        enter = fadeIn(
                            animationSpec = tween(
                                durationMillis = time,
                                easing = LinearEasing,
                                delayMillis = 100
                            )
                        ),
                        exit = fadeOut(
                            animationSpec = tween(
                                durationMillis = time,
                                easing = LinearEasing,
                                delayMillis = 100
                            )
                        )

                    ) {
                        CoilImage(
                            imageModel = Constants.URL.BACKDROP_URL + movie.backdrop
                        )
                    }
                }
            }
        }
    }

}