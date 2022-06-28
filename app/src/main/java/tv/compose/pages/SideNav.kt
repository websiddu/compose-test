package tv.compose.pages

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.coil.CoilImage
import tv.compose.ui.components.*
import tv.compose.ui.focus.FocusableSurface
import tv.compose.ui.focus.SurfaceStyle

@Composable
fun SideNavDemo(navController: NavController) {
  
  val image =
    "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcS0mse-zZ89oUm8W8Pe5kr4paP6hzLC5jDFpsCFm073osg3CWp0"
  
  Row(
    modifier = Modifier
      .fillMaxSize()
  ) {
    val navigationDrawerState = rememberNavigationDrawerState()
    
    val width =
      animateDpAsState(
        targetValue = if (navigationDrawerState.drawerState.value == TVDrawerState.OPEN) 220.dp else 80.dp,
        animationSpec = tween(300)
      )
    
    
    
    Column() {
      TVNavigationDrawer(
        modifier = Modifier
          .background(MaterialTheme.colorScheme.surfaceTint.copy(0.04f))
          .width(width.value)
          .defaultMinSize(minWidth = 80.dp)
          .padding(horizontal = 12.dp, vertical = 24.dp)
          .fillMaxHeight(),
        navigationDrawerState = navigationDrawerState,
        footer = {
          ListItem(
            onPress = {},
            label = "Settings",
            icon = Icons.Outlined.Settings,
            hideLabel = it == TVDrawerState.CLOSE
          )
        }
      ) {
        item {
          ListItem(
            onPress = {
              navigationDrawerState.drawerState.value = TVDrawerState.CLOSE
            },
            label = "Home",
            icon = Icons.Outlined.Home,
            hideLabel = it == TVDrawerState.CLOSE
          )
        }
        item {
          ListItem(
            onPress = {},
            label = "Movies",
            icon = Icons.Outlined.Movie,
            hideLabel = it == TVDrawerState.CLOSE
          )
        }
        item {
          ListItem(
            onPress = {},
            label = "Shows",
            icon = Icons.Outlined.Slideshow,
            hideLabel = it == TVDrawerState.CLOSE
          )
        }
        item {
          ListItem(
            onPress = {},
            label = "Trending",
            icon = Icons.Outlined.Whatshot,
            hideLabel = it == TVDrawerState.CLOSE
          )
        }
        item {
          ListItem(
            onPress = {},
            label = "Live",
            icon = Icons.Outlined.Sensors,
            hideLabel = it == TVDrawerState.CLOSE
          )
        }
      }
    }
    
    Column(
      modifier = Modifier
        .width(880.dp)
        .horizontalScroll(rememberScrollState())
    ) {
      
      Row(modifier = Modifier.padding(18.dp, 24.dp, 0.dp, 24.dp)) {
        FocusableSurface(
          onPress = { /*TODO*/ },
          focusStyle = SurfaceStyle(scale = 1f, outlineWidth = 2.dp, outlineInset = 4.dp),
          shape = RoundedCornerShape(12.dp),
          outlineShape = RoundedCornerShape(14.dp)
        ) {
          Box(
            modifier = Modifier
              .width(844.dp)
              .height(300.dp)
          ) {
            CoilImage(
              imageModel = image,
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
            )
          }
        }
      }
  
  
      Row(modifier = Modifier.padding(18.dp, 24.dp, 0.dp, 24.dp)) {
  
        TvButton(onPress = { /*TODO*/ }) {
          Text(text = "Button 1")
        }
        Spacer(modifier = Modifier.size(40.dp))
        TvButton(onPress = { /*TODO*/ }) {
          Text(text = "Button 2")
        }
        
      }
      
      


//
//      TvLazyRow() {
//        item {
//          FeaturedCarouselPage()
//        }
//        item {
//          CardsPage(navController = navController)
//        }
//      }
//
      
   
    }
    
  }
}
