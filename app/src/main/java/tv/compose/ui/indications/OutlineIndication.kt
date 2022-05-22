package tv.compose.ui.indications

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


private class OutlineIndicationInstance(
    private val isFocused: State<Boolean>,
    private val shape: Shape,
    private val outlineWidth: Dp,
    private val outlineInset: Dp,
    private val outlineColor: Color
) : IndicationInstance {
    override fun ContentDrawScope.drawIndication() {
        drawContent()
        if (isFocused.value) {

            inset(-outlineInset.toPx(), -outlineInset.toPx()) {
                drawOutline(
                    outline = shape.createOutline(
                        size = size,
                        layoutDirection,
                        this
                    ),
                    brush = SolidColor(outlineColor),
                    alpha = 1.0f,
                    style = Stroke(width = outlineWidth.toPx())
                )
            }

        }
    }
}


class OutlineIndication(
    private val shape: Shape = RectangleShape,
    private val outlineWidth: Dp = 2.dp,
    private val outlineInset: Dp = 0.dp,
    private val outlineColor: Color = Color.Blue
) : Indication {

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val isFocused = interactionSource.collectIsFocusedAsState()
        return remember(interactionSource) {
            OutlineIndicationInstance(
                isFocused,
                shape = shape,
                outlineWidth = outlineWidth,
                outlineColor = outlineColor,
                outlineInset = outlineInset
            )
        }
    }
}