package tv.compose.ui.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import tv.compose.ui.focus.SurfaceStyle

data class FocusTheme(
    val scaleFactor: Float = 1.1f,
    val outlineWidth: Dp = 2.dp,
    val outlineInset: Dp = 0.dp,
    val focusTime: Int = 200,
)


public  interface FocusIndicationTheme {
    @Composable
    public fun defaultScaleFactor(): Float

    @Composable
    public fun outlineSize(): Dp

    @Composable
    public fun outlineInset(): Dp


}

val LocalFocusTheme = compositionLocalOf { FocusTheme() }

val MaterialTheme.focus: FocusTheme
    @Composable
    @ReadOnlyComposable
    get() = LocalFocusTheme.current