package tv.compose.ui.components


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <Key> Pager(
  modifier: Modifier = Modifier,
  enterTransition: EnterTransition = fadeIn(animationSpec = tween(900)),
  exitTransition: ExitTransition = fadeOut(animationSpec = tween(900)),
  frameIndexToDisplay: MutableState<Key>,
  content: PagerScope<Key>.() -> Unit
) {
  val frames by remember { mutableStateOf(PagerScope<Key>().apply(content).getMap())}
  
  if (frames.isEmpty()) {
    return
  }
  
  AnimatedContent(
    targetState = frameIndexToDisplay.value,
    transitionSpec = { enterTransition.with(exitTransition) }
  ) {
    Box(modifier) {
      frames[it]?.invoke(this)
    }
  }
}

class PagerScope<Key>: MapOfScope<Key, @Composable BoxScope.() -> Unit>() {
  fun frame(key: Key, frame: @Composable BoxScope.() -> Unit)  = tuple(key, frame)
}

abstract class MapOfScope<Key, Value>{
  private val internalMap = mutableMapOf<Key, Value>()
  
  fun tuple(key: Key, value: Value) { internalMap[key] = value }
  fun getMap(): Map<Key, Value> = internalMap
}

class FCScope : ListOfScope<@Composable BoxScope.() -> Unit>()


