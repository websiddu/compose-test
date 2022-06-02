package tv.compose.ui.components

import android.content.res.AssetManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import tv.compose.R
import tv.compose.ui.focus.ChildFocusState
import tv.compose.ui.focus.FocusableSurface
import tv.compose.ui.focus.SurfaceStyle

@Composable
fun TvCard(
  image: String = "",
  name: String = "",
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
      
      CoilImage(
        imageModel = image,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .height(110.dp)
          .aspectRatio(16f / 9f)
      )
      
    }
    
    CompositionLocalProvider(
      LocalContentColor provides MaterialTheme.colorScheme.onBackground
    ) {
      
      Column(modifier = Modifier
        .width(196.dp)
        .padding(0.dp, 12.dp, 0.dp, 0.dp)) {
        Text(
          text = name,
          style = MaterialTheme.typography.bodySmall,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Text(
          text = "Disney +",
          style = MaterialTheme.typography.labelSmall
        )
      }
    }
    
  }
  
}

@Composable
fun TvWideCard(
  image: String = "",
  state: ChildFocusState = ChildFocusState(),
  content: @Composable () -> Unit,
) {
  
  
  CompositionLocalProvider(
    LocalContentColor provides MaterialTheme.colorScheme.onBackground,
  ) {
    FocusableSurface(
      onPress = { /*TODO*/ },
      state = state,
      shape = RoundedCornerShape(16.dp),
      focusStyle = SurfaceStyle(
        scale = 1.05f
      )
    ) {
      Row() {
        Image(
          painter = painterResource(R.drawable.poster),
          contentDescription = null,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .height(110.dp)
            .aspectRatio(
              matchHeightConstraintsFirst = true,
              ratio = 1.77778f
            )
        )
        
        Column(modifier = Modifier.padding(16.dp)) {
          Text(text = "Title", color = MaterialTheme.colorScheme.onBackground)
          Text(text = "Secondary")
          Text(text = "Long description...")
        }
      }
    }
  }
}