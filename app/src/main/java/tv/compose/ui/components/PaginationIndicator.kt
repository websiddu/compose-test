package tv.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun PaginationIndicator(
  modifier: Modifier = Modifier,
  activeColor: Color = LocalContentColor.current.copy(alpha = 1.0f),
  inactiveColor: Color = activeColor.copy(0.5f),
  indicatorWidth: Dp = 8.dp,
  indicatorHeight: Dp = indicatorWidth,
  spacing: Dp = indicatorWidth,
  indicatorShape: Shape = CircleShape,
  pageCount: Int,
  frameIndexToDisplay: MutableState<Int>
) {
  val indicatorWidthPx = LocalDensity.current.run { indicatorWidth.roundToPx() }
  val spacingPx = LocalDensity.current.run { spacing.roundToPx() }
  
  Box(modifier = modifier) {
    
    Row(
      horizontalArrangement = Arrangement.spacedBy(spacing),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      val inactiveIndicator = Modifier
        .size(height = indicatorHeight, width = indicatorWidth)
        .background(color = inactiveColor, shape = indicatorShape)
      
      repeat(pageCount) {
        Box(inactiveIndicator)
      }
    }
    
    Box(
      Modifier
        .offset {
          IntOffset(
            x = ((spacingPx + indicatorWidthPx) * frameIndexToDisplay.value),
            y = 0
          )
        }
        .size(width = indicatorWidth, height = indicatorHeight)
        .background(
          color = activeColor,
          shape = indicatorShape,
        )
    )
  }
}