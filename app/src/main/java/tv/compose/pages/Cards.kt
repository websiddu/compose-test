package tv.compose.pages

import android.content.Context
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.tv.compose.foundation.PivotOffsets
import androidx.tv.compose.foundation.lazy.list.TvLazyColumn
import androidx.tv.compose.foundation.lazy.list.TvLazyRow
import androidx.tv.compose.foundation.lazy.list.items
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tv.compose.ui.components.RestoringFocusDemo
import tv.compose.ui.components.TvCard
import tv.compose.ui.components.TvWideCard
import tv.compose.ui.focus.ChildFocusState
import tv.compose.ui.focus.restoreFocusParent
import tv.compose.utils.getJsonDataFromAsset


data class Cluster(val label: String = "Untitled", val items: List<MediaItem>) {}
data class MediaItem(val name: String, val poster: String, val id: String, val type: String) {}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardsPage() {
  
  val jsonFileString = getJsonDataFromAsset(LocalContext.current, "clusters.json")
  
  val gson = Gson()
  
  val clusterType = object : TypeToken<List<Cluster>>() {}.type
  val clusters: List<Cluster> = gson.fromJson(jsonFileString, clusterType)
  
  
  Column() {

    RestoringFocusDemo()

//    TvLazyColumn(
//      contentPadding = PaddingValues(0.dp, 20.dp),
//      verticalArrangement = Arrangement.spacedBy(32.dp),
//      modifier = Modifier.focusGroup()
//    ) {
//
//      items(clusters) { item ->
//
//        var hasFocused by remember { mutableStateOf(false) }
//
//        val fontSize = animateFloatAsState(
//          targetValue = if (hasFocused) 28f else 12f,
//          animationSpec = tween(durationMillis = 200)
//        )
//
//        Text(
//          text = item.label,
//          modifier = Modifier
//            .padding(58.dp, 0.dp, 0.dp, 20.dp),
//          fontSize= 16.sp,
//          fontWeight = FontWeight.Medium,
//          color = MaterialTheme.colorScheme.onBackground
//        )
//
//        TvLazyRow(
//          horizontalArrangement = Arrangement.spacedBy(20.dp),
//          contentPadding = PaddingValues(58.dp, 0.dp),
//          modifier = Modifier.onFocusChanged {
//            hasFocused = it.hasFocus
//          }
//        ) {
//          items(item.items) { card ->
//            TvCard(image = card.poster, name = card.name) {}
//          }
//        }
//      }
//    }
  }
}

