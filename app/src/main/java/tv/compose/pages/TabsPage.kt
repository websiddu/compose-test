package tv.compose.pages

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.tv.compose.foundation.lazy.list.TvLazyColumn
import androidx.tv.compose.foundation.lazy.list.TvLazyRow
import androidx.tv.compose.foundation.lazy.list.items
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tv.compose.data.model.Movie
import tv.compose.data.model.TvShow
import tv.compose.pages.jetstream.FeaturedCarousel
import tv.compose.ui.components.TvButton
import tv.compose.ui.components.TvCard
import tv.compose.ui.focus.ChildFocusState
import tv.compose.ui.focus.restoreFocusParent
import tv.compose.ui.vm.Loading
import tv.compose.ui.vm.Success
import tv.compose.ui.vm.TmdbViewModel
import tv.compose.ui.vm.UiState
import tv.compose.utils.Constants


data class SlideInOutAnimationState<T>(
    val key: T,
    val content: @Composable () -> Unit
)

@Composable
fun <T> CrossSlide(
    targetState: T,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Offset> = tween(2000),
    reverseAnimation: Boolean = false,
    content: @Composable (T) -> Unit
) {
    val direction: Int = if (reverseAnimation) -1 else 1

    val items = remember { mutableStateListOf<SlideInOutAnimationState<T>>() }
    val transitionState = remember { MutableTransitionState(targetState) }
    val targetChanged = (targetState != transitionState.targetState)
    transitionState.targetState = targetState
    val transition: Transition<T> = updateTransition(transitionState, label = "")

    if (targetChanged || items.isEmpty()) {
        // Only manipulate the list when the state is changed, or in the first run.
        val keys = items.map { it.key }.run {
            if (!contains(targetState)) {
                toMutableList().also { it.add(targetState) }
            } else {
                this
            }
        }
        items.clear()
        keys.mapTo(items) { key ->

            SlideInOutAnimationState(key) {

                val opacity by transition.animateFloat(
                    transitionSpec = {
                        tween(
                            durationMillis = 400,
                            easing = CubicBezierEasing(0.12f, 1.00f, 0.40f, 1.00f)
                        )
                    },
                    label = ""
                )
                {
                    if (it == key) 1f
                    else 0f
                }


                val xTransition by transition.animateOffset(
                    transitionSpec = {
                        tween(
                            durationMillis = 500,
                            easing = CubicBezierEasing(0.12f, 1.00f, 0.40f, 1.00f)
                        )
                    },
                    label = ""
                ) {
                    if (it == key) Offset(0f, 0f)
                    else Offset(200f, 0f)
                }

                Box(modifier.graphicsLayer {
                    if (transition.targetState == key) {
                        this.translationX = direction * xTransition.x
                        this.alpha = opacity
                    } else {
                        this.translationX = direction * -xTransition.x
                        this.alpha = opacity
                    }
                }) {
                    content(key)
                }
            }
        }
    } else if (transitionState.currentState == transitionState.targetState) {
        // Remove all the intermediate items from the list once the animation is finished.
        items.removeAll { it.key != transitionState.targetState }
    }

    Box(modifier) {
        items.forEach {
            key(it.key) {
                it.content()
            }
        }
    }
}

@Composable
fun ShowTab(state: UiState<List<TvShow>>, navController: NavController, viewModel: TmdbViewModel) {

    val focusSate = remember { ChildFocusState() }


    when (state) {
        is tv.compose.ui.vm.Error -> {
            Text("Error...")
        }
        is Loading -> {
            Text(text = "Loading...")
        }
        is Success -> {
            val data = (state as Success).data

            Column() {
                Box(
                    modifier = Modifier
                        .padding(58.dp, 0.dp)
                        .height(400.dp)
                        .fillMaxWidth()
                ) {
                    FeaturedCarousel(viewModel)
                }
                TvLazyRow(
                    contentPadding = PaddingValues(58.dp, 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.restoreFocusParent(focusSate)
                ) {
                    items(data) { item ->
                        TvCard(
                            onClick = {
                                navController.navigate("jetstream/e/${item.tvShowId}")
                            },
                            state = focusSate,
                            image = Constants.URL.POSTER_URL + item.poster,
                            name = item.name,
                            width = 140.dp,
                            aspectRatio = 2f / 3f
                        ) {
                        }
                    }
                }
            }
        }
        else -> {}
    }
}

@Composable
fun MovieTab(state: UiState<List<Movie>>, navController: NavController) {

    val focusSate = remember { ChildFocusState() }

    when (state) {
        is tv.compose.ui.vm.Error -> {
            Text("Error...")
        }
        is Loading -> {
            Text(text = "Loading...")
        }
        is Success -> {
            val data = (state as Success).data
            TvLazyRow(
                contentPadding = PaddingValues(58.dp, 20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.restoreFocusParent(focusSate)
            ) {
                items(data) { item ->
                    TvCard(
                        image = Constants.URL.POSTER_URL + item.poster,
                        name = item.title,
                        state = focusSate,
                        width = 140.dp,
                        aspectRatio = 2f / 3f,
                    ) {
                    }
                }
            }
        }
        else -> {}
    }
}

@Composable
fun SlideFade(
    tabs: List<String>,
    currentTab: Int,
    previousTab: Int,
    viewModel: TmdbViewModel,
    navController: NavController
) {

    val enterEasing = CubicBezierEasing(0.12f, 1.00f, 0.40f, 1.00f)
    val exitEasing = CubicBezierEasing(0.40f, 1.00f, 0.12f, 1.00f)
    val duration = 600
    val enterOffset = 300
    val exitOffset = 200
    val direction = if (previousTab > currentTab) -1 else 1

    val showsState: UiState<List<TvShow>> by viewModel.getTvShows()
        .observeAsState(initial = Loading())

    val moviesState: UiState<List<Movie>> by viewModel.getMovies()
        .observeAsState(initial = Loading())



    Box(modifier = Modifier.fillMaxWidth()) {
        tabs.forEachIndexed { index, label ->
            AnimatedVisibility(
                visible = currentTab == index,
                enter = slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = duration,
                        easing = enterEasing
                    ),
                    initialOffsetX = { enterOffset * direction }) + fadeIn(
                    animationSpec = tween(
                        durationMillis = duration,
                        easing = enterEasing
                    )
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = duration,
                        easing = exitEasing
                    ),
                    targetOffsetX = { -exitOffset * direction }) + fadeOut(
                    animationSpec = tween(
                        durationMillis = duration,
                        easing = exitEasing
                    )
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    if (index == 0) {
                        ShowTab(showsState, navController, viewModel)
                    }

                    if (index == 1) {
                        MovieTab(moviesState, navController)
                    }
                    if (index == 2) {
                        Text(text = "Another tab content goes here...")
                    }
                    if (index == 3) {
                        Text(text = "Library content")
                    }
                }
            }
        }
    }
}


@Composable
fun TabsPage(viewModel: TmdbViewModel, navController: NavController) {

    var currentTab by remember { mutableStateOf(0) }
    var previousTab by remember { mutableStateOf(0) }

    val tabs = listOf<String>("Home", "Movies", "Shows", "Live");


    LaunchedEffect(true) {
        viewModel.refreshTvShows()
        viewModel.refreshMovies()
    }

    Column(modifier = Modifier.fillMaxSize()) {


        TabRow(selectedTabIndex = currentTab) {
            tabs.forEachIndexed { index, label ->
                Tab(selected = false, modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            previousTab = currentTab
                            currentTab = index
                        }
                    }
                    .focusable(), onClick = {
                    currentTab = index
                }) {
                    Text(text = label)
                }
            }
        }


        SlideFade(tabs, currentTab, previousTab, viewModel, navController)


//    CrossSlide(
//      targetState = currentTab,
//      reverseAnimation = previousTab > currentTab,
//      modifier = Modifier
//        .fillMaxWidth()
//    ) { screen ->
//      when (screen) {
//        0 -> {
//          Box(
//            modifier = Modifier
//              .background(Color.Red)
//              .fillMaxSize()
//          ) {
//            Text("Page A")
//          }
//        }
//        1 -> {
//          Box(
//            modifier = Modifier
//              .background(Color.Green)
//              .fillMaxSize()
//          ) {
//            Text("Page B")
//          }
//        }
//        2 -> Text("Page C")
//        3 -> Text("Page D")
//      }
//    }

    }


}


