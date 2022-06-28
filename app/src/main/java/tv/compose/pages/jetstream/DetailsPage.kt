package tv.compose.pages.jetstream

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.tv.compose.foundation.lazy.list.TvLazyColumn
import com.skydoves.landscapist.coil.CoilImage
import tv.compose.data.model.TvShow
import tv.compose.ui.vm.*
import tv.compose.utils.Constants

@Composable
fun DetailsPage(
  navController: NavController, viewModel: TmdbViewModel, id: String,
  navigateUp: () -> Unit
) {
  
  LaunchedEffect(true) {
    viewModel.getTvShowById(id.toInt())
  }
  
  val showState: UiState<TvShow> by viewModel.getTvShowById(id.toInt())
    .observeAsState(initial = Loading())
  
  TvLazyColumn(modifier = Modifier.fillMaxSize()) {
    when (showState) {
      is Error -> {
        
      }
      is Loading -> {
        item {
          CircularProgressIndicator()
        }
      }
      is Success -> {
        val data = (showState as Success).data
        
        item {
          Hero(data)
        }
        item {
          CastAndCrew()
        }
        item {
          Reviews()
        }
      }
    }
  }
}


@Composable
fun Hero(data: TvShow) {
  
  
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Color.Black
      )
  ) {
    CoilImage(
      imageModel = Constants.URL.BACKDROP_URL + data.backdrop,
      modifier = Modifier.fillMaxSize(),
    )
    
    Box(
      modifier = Modifier
        .height(540.dp)
        .width(960.dp)
        .offset(0.dp, 0.dp)
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(
              Color.Transparent,
              Color.Black
            )
          )
        )
    ) {
    }
    
    
    Column(modifier = Modifier.offset(58.dp, 320.dp)) {
      Text(
        text = data.name, style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.inverseOnSurface
      )
      
      Spacer(modifier = Modifier.height(20.dp))
      
      Text(
        text = data.overview,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.inverseOnSurface,
        modifier = Modifier.fillMaxWidth(0.75f),
        maxLines = 3
      )
    }
  }
  
}

@Composable
fun CastAndCrew() {
  
}

@Composable
fun Reviews() {
  
}