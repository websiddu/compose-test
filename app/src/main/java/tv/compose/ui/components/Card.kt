package tv.compose.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.Default
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import tv.compose.R
import tv.compose.ui.focus.ChildFocusState
import tv.compose.ui.focus.FocusableSurface
import tv.compose.ui.focus.SurfaceStyle


private fun Modifier.focusableWithBorder() = composed {
    var borderColor by remember { mutableStateOf(Color.Black) }
    Modifier
        .onFocusChanged { borderColor = if (it.isFocused) Color.Red else Color.Black }
        .border(5.dp, borderColor)
        .focusable()
}


@Composable
fun RestoringFocusDemo() {
    val lazyRowStateList = remember { List(10) { TvLazyRowState() } }
    LazyColumn {
        items(10) { rowIndex ->
            TvTLazyRow(lazyRowStateList[rowIndex]) { itemIndex ->
                Box(
                    Modifier
                        .size(100.dp)
                        .focusableWithBorder()) {
                    Text("$itemIndex", Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TvTLazyRow(state: TvLazyRowState, content: @Composable (Int) -> Unit) {


    LazyRow(
        Modifier
            .focusProperties {
                var enter = { state.focusRequesterMap[state.selectedIndex] ?: Default }
            }
            .focusGroup()) {
        items(100) { index ->
            val focusRequester = remember(index) { FocusRequester() }
            Box(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.hasFocus) {
                            state.selectedIndex = index
                        }
                    }
            ) {
                // To keep the example simple, I am sending the item index to the user through
                // this content lambda. This should be done using the items function in the
                // receiver scope of TvLazyList.
                content.invoke(index)
            }
            DisposableEffect(index) {
                state.focusRequesterMap[index] = focusRequester
                onDispose { state.focusRequesterMap.remove(index) }
            }
        }
    }
}

class TvLazyRowState(
    var selectedIndex: Int = 0,
    val focusRequesterMap: MutableMap<Int, FocusRequester> = mutableMapOf()
)

@Composable
fun TvCard(
    image: String = "",
    name: String = "",
    state: ChildFocusState = remember { ChildFocusState() },
    onFocus: () -> Unit = {},
    onClick: () -> Unit = {},
    aspectRatio: Float = 16f / 9f,
    width: Dp = 200.dp,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }



    Column {

        FocusableSurface(
            onFocus = onFocus,
            onPress = onClick,
            shape = RoundedCornerShape(0.dp),
            outlineShape = RoundedCornerShape(0.dp),
            focusStyle = SurfaceStyle(
                scale = 1.2f,
                outlineWidth = 5.dp,
                outlineInset = 5.dp
            ),
            state = state
        ) {

            CoilImage(
                imageModel = image,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(width)
                    .aspectRatio(aspectRatio)
            )

        }

        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onBackground
        ) {

            Column(
                modifier = Modifier
                    .width(width)
                    .padding(0.dp, 12.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Disney +",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

    }

}

@Composable
fun TvWideCard(
    image: String = "",
    state: ChildFocusState = ChildFocusState(),
    content: @Composable () -> Unit,
) {


    CompositionLocalProvider(
        LocalContentColor provides MaterialTheme.colorScheme.onBackground,
    ) {
        FocusableSurface(
            onPress = { /*TODO*/ },
            state = state,
            shape = RoundedCornerShape(16.dp),
            focusStyle = SurfaceStyle(
                scale = 1.05f
            )
        ) {
            Row() {
                Image(
                    painter = painterResource(R.drawable.poster),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(110.dp)
                        .aspectRatio(
                            matchHeightConstraintsFirst = true,
                            ratio = 1.77778f
                        )
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Title", color = MaterialTheme.colorScheme.onBackground)
                    Text(text = "Secondary")
                    Text(text = "Long description...")
                }
            }
        }
    }
}