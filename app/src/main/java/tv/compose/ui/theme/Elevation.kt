package tv.compose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Elevation (
    val Level0: Dp = 0.0.dp,
    val Level1: Dp = 1.0.dp,
    val Level2: Dp = 3.0.dp,
    val Level3: Dp = 6.0.dp,
    val Level4: Dp = 8.0.dp,
    val Level5: Dp = 12.0.dp
)

val LocalElevation = compositionLocalOf { Elevation() }

val MaterialTheme.elevation: Elevation
    @Composable
    @ReadOnlyComposable
    get() = LocalElevation.current