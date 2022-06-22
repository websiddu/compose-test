package tv.compose.pages

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.tv.compose.foundation.lazy.list.TvLazyColumn
import androidx.tv.compose.foundation.lazy.list.TvLazyRow
import androidx.tv.compose.foundation.lazy.list.items
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.skydoves.landscapist.coil.CoilImage
import tv.compose.ui.components.TvCard
import tv.compose.ui.focus.FocusableSurface
import tv.compose.ui.focus.SurfaceStyle
import tv.compose.utils.getJsonDataFromAsset

data class ImmersiveItem(
  val name: String,
  val poster: String,
  val backdrop: String,
  val id: String,
  val type: String
) {}


@Composable
fun Details(item: ImmersiveItem) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(280.dp)
  ) {
    
    
    CoilImage(
      imageModel = item.backdrop,
      contentScale = ContentScale.FillWidth,
      alignment = Alignment.TopEnd,
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(16f / 9f)
    )
    
    
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(16f / 9f)
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(
              Color.Transparent, Color.White
            )
          )
        )
    )
    
    Text(
      text = item.name,
      style = MaterialTheme.typography.displaySmall,
      modifier = Modifier.offset(x = 40.dp, y = 220.dp)
    )
  }
  
}

@Composable
fun Background() {

}


@Composable
fun ImmersiveClusterPage() {
  
  val jsonFileString = getJsonDataFromAsset(LocalContext.current, "immersive.json")
  
  val gson = Gson()
  
  val immersiveItemType = object : TypeToken<List<ImmersiveItem>>() {}.type
  val items: List<ImmersiveItem> = gson.fromJson(jsonFileString, immersiveItemType)
  
  
  var current by remember { mutableStateOf(items[0]) }
  
  TvLazyColumn() {
  
    item {
      TvLazyRow(
        contentPadding = PaddingValues(40.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        items(items) { card ->
          TvCard(image = card.poster, name = card.name, onFocus = {
          
          }) {}
        }
      }
    }
    
    item {
      Details(current)
    }
    
    item {
      TvLazyRow(
        contentPadding = PaddingValues(40.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        items(items) { card ->
          TvCard(image = card.poster, name = card.name, onFocus = {
            current = card
          }) {}
        }
      }
    }
  }
}