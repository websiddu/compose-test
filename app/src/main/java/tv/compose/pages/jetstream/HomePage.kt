package tv.compose.pages.jetstream

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.tv.compose.foundation.lazy.list.TvLazyColumn
import androidx.tv.compose.foundation.lazy.list.TvLazyListState
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.skydoves.landscapist.coil.CoilImage
import tv.compose.data.model.Movie
import tv.compose.pages.SlideFade
import tv.compose.ui.components.PagerState
import tv.compose.ui.components.TvButton
import tv.compose.ui.focus.ChildFocusState
import tv.compose.ui.focus.FocusableSurface
import tv.compose.ui.focus.SurfaceStyle
import tv.compose.ui.focus.restoreFocusParent
import tv.compose.ui.vm.Loading
import tv.compose.ui.vm.Success
import tv.compose.ui.vm.TmdbViewModel
import tv.compose.ui.vm.UiState
import tv.compose.utils.Constants

@Composable
fun NavTab(
    label: String,
    onFocus: () -> Unit,
    state: ChildFocusState? = null,
    selected: Boolean = false,
) {

    val style = if (!selected) SurfaceStyle(
        backgroundColor = Color.Unspecified,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) else SurfaceStyle(
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    )

    FocusableSurface(
        onFocus = onFocus,
        modifier = Modifier.padding(16.dp, 8.dp),
        shape = CircleShape,
        style = style,
        state = state,
        focusStyle = SurfaceStyle(
            scale = 1.05f,
            shadowElevation = 8.dp
        )
    ) {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun TopNav(
    tabs: List<String> = listOf(),
    currentTab: MutableState<Int>,
    previousTab: MutableState<Int>
) {
    val focusSate = remember { ChildFocusState() }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(58.dp, 0.dp)
    ) {
        Spacer(modifier = Modifier.size(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(0.dp, 24.dp)
                .restoreFocusParent(focusSate)
        ) {
            tabs.forEachIndexed { index, label ->
                NavTab(
                    label = label,
                    selected = currentTab.value == index,
                    state = focusSate,
                    onFocus = {
                        previousTab.value = currentTab.value
                        currentTab.value = index
                    }
                )
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FeaturedCarousel(viewModel: TmdbViewModel) {


    val enterEasing = CubicBezierEasing(0.12f, 1.00f, 0.40f, 1.00f)
    val exitEasing = CubicBezierEasing(0.40f, 1.00f, 0.12f, 1.00f)
    val duration = 600
    val enterOffset = 300
    val exitOffset = 200

    val moviesState: UiState<List<Movie>> by viewModel.getMovies()
        .observeAsState(initial = Loading())

    when (moviesState) {
        is tv.compose.ui.vm.Error -> {}
        is Loading -> {}
        is Success -> {
            val data = (moviesState as Success).data

            val focusRequester = remember { FocusRequester() }
            var currentItem by remember { mutableStateOf(0) }
            var previousItem by remember { mutableStateOf(data.count() - 1) }

            val direction = if (currentItem < previousItem) -1 else 1

            FocusableSurface(
                focusRequester = focusRequester,
                shape = RoundedCornerShape(20.dp),
                outlineShape = RoundedCornerShape(22.dp),
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
                focusStyle = SurfaceStyle(
                    scale = 1.01f,
                    outlineInset = 4.dp,
                    outlineWidth = 2.dp
                ),
                modifier = Modifier.fillMaxSize()
            ) {


                Crossfade(
                    targetState = currentItem,
                    animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                ) { it ->

                    val context = LocalContext.current

                    CoilImage(
                        imageRequest = ImageRequest.Builder(context)
                            .data(Constants.URL.BACKDROP_URL + data[it].backdrop)
                            .crossfade(true)
                            .build(),
                        imageLoader = {
                            ImageLoader.Builder(context)
                                .memoryCache {
                                    MemoryCache.Builder(context).maxSizePercent(0.25).build()
                                }
                                .diskCache {
                                    DiskCache.Builder().maxSizePercent(0.1)
                                        .directory(context.cacheDir).build()
                                }
                                .crossfade(true)
                                .build()
                        },
                        contentScale = ContentScale.Crop
                    )

                }


                data.forEachIndexed { index, movie ->
                    AnimatedVisibility(
                        visible = index == currentItem,
                        enter = slideInHorizontally(
                            animationSpec = tween(durationMillis = 500, easing = enterEasing)
                        ) {
                            direction * enterOffset
                        } + fadeIn(
                            animationSpec = tween(durationMillis = 500, easing = enterEasing)
                        ),
                        exit = slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = exitEasing
                            )
                        ) {
                            direction * -exitOffset
                        } + fadeOut(
                            animationSpec = tween(durationMillis = 500, easing = enterEasing)
                        )
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .absolutePadding(40.dp, 0.dp, 40.dp, 40.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .align(Alignment.BottomStart)
                            ) {
                                Text(
                                    text = movie.title,
                                    style = MaterialTheme.typography.displayMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                TvButton(onPress = { /*TODO*/ }) {
                                    Text(text = "Play")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun HomePage(navController: NavController, viewModel: TmdbViewModel, pageState: TvLazyListState) {
    val currentTab = remember { mutableStateOf(0) }
    val previousTab = remember { mutableStateOf(0) }
    val tabs = listOf<String>("Home", "Movies", "Shows", "Live");

    TvLazyColumn(modifier = Modifier.fillMaxSize(), state = pageState) {
        item {
            TopNav(tabs, currentTab, previousTab)
        }
        item {
            SlideFade(tabs, currentTab.value, previousTab.value, viewModel, navController)
        }
    }
}