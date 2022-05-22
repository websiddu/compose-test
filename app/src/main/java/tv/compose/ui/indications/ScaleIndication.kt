package tv.compose.ui.indications

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import kotlinx.coroutines.launch
import tv.compose.ui.theme.focus

internal  object ScaleIndicationTokens {
    const val focusDuration: Int = 300
    const val unFocusDuration: Int = 500
    const val pressedDuration: Int = 120
    const val releaseDuration: Int = 300
    val enterEasing = CubicBezierEasing(0f, 0f, 0.2f, 1f)
}

private class ScaleIndicationInstance(private val scale: Float) : IndicationInstance {
    val animatedScalePercent = Animatable(1f)

    suspend fun animateToFocused() {
        animatedScalePercent.animateTo(
            scale,
            tween(
                durationMillis = ScaleIndicationTokens.focusDuration,
                easing = ScaleIndicationTokens.enterEasing
            )
        )
    }

    suspend fun animateToResting() {
        animatedScalePercent.animateTo(
            1f,
            tween(
                durationMillis = ScaleIndicationTokens.unFocusDuration,
                easing = ScaleIndicationTokens.enterEasing
            )
        )
    }

    suspend fun animateToPressed() {
        animatedScalePercent.animateTo(
            1f,
            tween(
                durationMillis = ScaleIndicationTokens.pressedDuration,
                easing = ScaleIndicationTokens.enterEasing
            )
        )
    }

    suspend fun animateToReleased() {
        animatedScalePercent.animateTo(
            scale,
            tween(
                durationMillis = ScaleIndicationTokens.releaseDuration,
                easing = ScaleIndicationTokens.enterEasing
            )
        )
    }

    override fun ContentDrawScope.drawIndication() {
        scale(
            scale = animatedScalePercent.value,
        ) {
            this@drawIndication.drawContent()
        }

    }
}

class ScaleIndication(private val scale: Float = 1.1f) : Indication {

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val instance = remember(interactionSource) { ScaleIndicationInstance(scale) }

        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->

                Log.e("my-log", interaction.toString())

                when (interaction) {

                    is FocusInteraction.Focus -> {
                        launch {
                            instance.animateToFocused()
                        }
                    }

                    is FocusInteraction.Unfocus -> {
                        launch {
                            instance.animateToResting()
                        }
                    }

                    is PressInteraction.Press -> {

                        launch {
                            instance.animateToPressed()
                        }
                    }

                    is PressInteraction.Release -> {
                        launch {
                            instance.animateToReleased()
                        }
                    }

                    is PressInteraction.Cancel -> {
                        launch {
                            instance.animateToReleased()
                        }
                    }
                }
            }
        }

        return instance
    }
}
