package tv.compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import tv.compose.R
import tv.compose.ui.focus.ChildFocusState
import tv.compose.ui.focus.FocusableSurface
import tv.compose.ui.focus.SurfaceStyle
import tv.compose.ui.focus.restoreFocusChild

@Composable
fun TvCard(
    image: String = "",
    state: ChildFocusState = ChildFocusState(),
    aspectRatio: Float = 1.777777778f,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column {

        FocusableSurface(
            onPress = { /*TODO*/ },
            shape = RoundedCornerShape(12.dp),
            outlineShape = RoundedCornerShape(16.dp),
            focusStyle = SurfaceStyle(
                scale = 1.1f,
                outlineWidth = 2.dp,
                outlineInset = 4.dp,
            ),
            state = state
        ) {
            Image(
                painter = painterResource(R.drawable.poster),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(90.dp)
                    .aspectRatio(matchHeightConstraintsFirst = true, ratio = aspectRatio)

            )
        }
    }

}