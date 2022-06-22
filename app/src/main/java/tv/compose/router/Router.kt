package tv.compose.router

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import tv.compose.pages.*
import tv.compose.pages.jetstream.DetailsPage
import tv.compose.ui.vm.TmdbViewModel


@Composable

fun Router(navController: NavHostController) {
  
  val viewModel: TmdbViewModel = hiltViewModel()
  
  CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
    Column {
      NavHost(navController = navController, startDestination = "home") {
        
        composable("home") {
          HomePage(navController = navController)
        }
        
        composable("colors") {
          ColorsPage(navController = navController)
        }
        
        composable("cards") {
          CardsPage()
        }
        
        composable("buttons") {
          ButtonsPage(navController = navController)
        }
        
        composable("typography") {
          TypographyPage()
        }
        
        composable("elevation") {
          ElevationPage()
        }
        
        composable("fc") {
          FeaturedCarouselPage()
        }
        
        composable("VerticalNav") {
//          VerticalNavPage(navController = navController)
          SideNavDemo(navController = navController)
        }
        
        composable("ImmersiveCluster") {
          ImmersiveClusterPage()
        }
        
        composable("Tabs") {
          TabsPage(viewModel = viewModel, navController = navController)
        }
        
        composable("jetstream/home") {
          tv.compose.pages.jetstream.HomePage(
            navController = navController,
            viewModel = viewModel
          )
        }
        
        composable("jetstream/e/{id}", arguments = listOf(
          navArgument("id") {
            type = NavType.StringType
          }
        )) {
          
          val id = remember {
            it.arguments?.getString("id")
          }
          
          
          DetailsPage(
            navController = navController,
            viewModel = viewModel,
            id = id.toString()
          )
        }
        
      }
    }
  }
}
