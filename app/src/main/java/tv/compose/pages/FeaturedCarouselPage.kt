package tv.compose.pages

import android.icu.number.Scale
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import tv.compose.R
import tv.compose.ui.components.AnimatedOverlay
import tv.compose.ui.components.Carousel
import tv.compose.ui.components.Pager


data class Media(
  val id: String = "",
  val title: String = "",
  val description: String = "",
  val image: String = ""
)

val mediaItems = listOf(
  Media(
    "1",
    "Turning Red",
    "Description 1",
    "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcS0mse-zZ89oUm8W8Pe5kr4paP6hzLC5jDFpsCFm073osg3CWp0"
  ),
  Media(
    "2",
    "Coco",
    "Description 2",
    "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQGi1AclG2lkAYX3632s1H77s7opDeBaeJ0CjLdxBlmV2Nnw-MP"
  ),
  Media(
    "3",
    "Encanto",
    "Description 3",
    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQnH1771XOI602me4UbbJbIr0I89oEzTXSlCG3ukQVWg-5wEMyL"
  )
);


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedCarouselPage() {
  
  val currentIndex: MutableState<Int> = remember {
    mutableStateOf(0)
  }
  
  Carousel(
    modifier = Modifier
      .fillMaxWidth(1f)
      .height(320.dp)
      .clip(RoundedCornerShape(20.dp))
      
  ) {
    mediaItems.forEachIndexed { index, item ->
      item() {
        AnimatedOverlay(
          modifier = Modifier.fillMaxSize(),
          backgroundComposable = {
            CoilImage(
              imageModel = item.image,
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
          },
          overlayComposable = {
            Box(
              modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(40.dp, 0.dp, 0.dp, 32.dp)
            ) {
              Column {
                Text(
                  text = item.title,
                  style = MaterialTheme.typography.displayMedium,
                  color = Color.White,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                  text = item.description,
                  style = MaterialTheme.typography.headlineSmall,
                  color = Color.White.copy(0.5f),
                )
              }
            }
            
          }
        )
      }
    }
  }
}