package tv.compose.ui.focus

import android.util.Log
import androidx.compose.runtime.Composable

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.input.key.*
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import tv.compose.ui.indications.OutlineIndication
import tv.compose.ui.indications.ScaleIndication
import tv.compose.ui.theme.elevation
import kotlin.math.ln


@Immutable
class SurfaceStyle(
  val scale: Float = 1f,
  val backgroundColor: Color = Color.Black,
  val contentColor: Color = Color.White,
  val tonalElevation: Dp = 0.dp,
  val shadowElevation: Dp = 0.dp,
  val outlineWidth: Dp = 0.dp,
  val outlineInset: Dp = 0.dp,
) {

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FocusableSurface(
  onPress: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = Shapes.None,
  outlineShape: Shape = Shapes.None,
  state: ChildFocusState? = null,
  style: SurfaceStyle = SurfaceStyle(
    backgroundColor = MaterialTheme.colorScheme.surface,
    contentColor = MaterialTheme.colorScheme.onSurface,
    tonalElevation = MaterialTheme.elevation.Level5
  ),
  focusStyle: SurfaceStyle = SurfaceStyle(
    backgroundColor = MaterialTheme.colorScheme.inverseSurface,
    contentColor = MaterialTheme.colorScheme.inverseOnSurface,
    tonalElevation = MaterialTheme.elevation.Level2,
    shadowElevation = 4.dp,
    scale = 1.1f,
  ),
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  focusRequester: FocusRequester = remember { FocusRequester() },
  content: @Composable () -> Unit
) {
  
  val isFocused by interactionSource.collectIsFocusedAsState()
  val currentStyle: SurfaceStyle = if (isFocused) focusStyle else style
  val scope = rememberCoroutineScope()
  var previousPress: PressInteraction.Press? by remember { mutableStateOf(null) }
  
  val dPadActions = Modifier.onKeyEvent {
    
    if (!listOf(Key.DirectionCenter, Key.Enter).contains(it.key)) {
      return@onKeyEvent false
    }
    
    when (it.type) {
      KeyEventType.KeyDown -> {
        val press = PressInteraction.Press(
          pressPosition = Offset(
            0f, 0f
          )
        );
        
        scope.launch {
          interactionSource.emit(press)
        }
        previousPress = press
        true
      }
      
      KeyEventType.KeyUp -> {
        previousPress?.let { previousPress ->
          
          val release = PressInteraction.Release(press = previousPress)
          scope.launch { interactionSource.emit(release) }
          onPress()
        }
        true
      }
      else -> {
        false
      }
    }
  };
  
  val clickable = Modifier.clickable(
    interactionSource = interactionSource,
    indication = null,
    enabled = true,
    role = Role.Button,
    onClick = onPress
  )
  
  CompositionLocalProvider(
    LocalContentColor provides currentStyle.contentColor,
  )
  {
    Box(
      modifier = Modifier
        .onFocusChanged {
          if (it.isFocused && state != null) {
            state.previouslyFocusedItem = focusRequester
          }
        }
        .focusableSurface(
          backgroundColor = surfaceColorAtElevation(
            color = currentStyle.backgroundColor,
            elevation = currentStyle.tonalElevation
          ),
          shape = shape,
          shadowElevation = currentStyle.shadowElevation,
          interactionSource = interactionSource,
          scale = focusStyle.scale,
          focusRequester = focusRequester,
          outlineWidth = focusStyle.outlineWidth,
          outlineInset = focusStyle.outlineInset,
          outlineShape = if (outlineShape != Shapes.None) outlineShape else shape
        )
        .then(clickable)
        .then(dPadActions)
        .then(modifier),
      propagateMinConstraints = true
    ) {
      content()
    }
  }
  
}

internal fun ColorScheme.surfaceColorAtElevation(
  elevation: Dp,
): Color {
  if (elevation == 0.dp) return surface
  val alpha = ((4.5f * ln(elevation.value + 1)) + 2f) / 100f
  return surfaceTint.copy(alpha = alpha).compositeOver(surface)
}

@Composable
private fun surfaceColorAtElevation(color: Color, elevation: Dp): Color {
  return if (color == MaterialTheme.colorScheme.surface) {
    MaterialTheme.colorScheme.surfaceColorAtElevation(elevation)
  } else {
    color
  }
}

private fun Modifier.focusableSurface(
  shape: Shape,
  backgroundColor: Color,
  shadowElevation: Dp,
  interactionSource: MutableInteractionSource,
  scale: Float,
  focusRequester: FocusRequester,
  outlineWidth: Dp,
  outlineShape: Shape,
  outlineInset: Dp
) =
  this
    .focusRequester(focusRequester)
    .indication(
      interactionSource = interactionSource,
      indication = if (scale != 1f) ScaleIndication(scale) else null
    )
    .indication(
      interactionSource = interactionSource,
      indication = if (outlineWidth != 0.dp) OutlineIndication(
        shape = outlineShape,
        outlineInset = outlineInset,
        outlineWidth = outlineWidth
      ) else null
    )
    .shadow(
      elevation = shadowElevation,
      shape = shape,
      clip = false,
      ambientColor = backgroundColor,
      spotColor = Color.Black
    )
    .background(color = backgroundColor, shape = shape)
    .clip(shape)
