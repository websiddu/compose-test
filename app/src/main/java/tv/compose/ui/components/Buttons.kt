package tv.compose.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import tv.compose.ui.focus.FocusableSurface


object FilledButtonTokens {
    val ContainerShape = Shapes.Full
}

object IconButtonTokens {
    val ContainerShape = Shapes.Full
}

@Composable
fun TvButton(
    onPress: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FilledButtonTokens.ContainerShape,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {

    FocusableSurface(
        onPress = { onPress() },
        modifier = modifier,
        shape = shape,
        interactionSource = interactionSource,
    ) {
        ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
            Row(
                Modifier
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight
                    )
                    .padding(20.dp, 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}


@Composable
fun TvIconButton(
    onPress: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = IconButtonTokens.ContainerShape,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    size: Dp = 40.dp,
    icon: ImageVector?,
    contentDescription: String = "",
    content: RowScope.() -> Unit
) {
    FocusableSurface(
        onPress = { onPress },
        modifier = modifier,
        shape = shape,
        interactionSource = interactionSource,
    ) {
        Row(
            Modifier.size(size),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(size/1.777f)
                )
            }
            content()
        }
    }
}


@Composable
fun ImageButton() {

}


@Composable
fun TextIconButton() {

}

@Composable
fun ListButton() {

}

