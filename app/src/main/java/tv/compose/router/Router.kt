package tv.compose.router

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tv.compose.pages.*


@Composable

fun Router(navController: NavHostController) {

    Column(
    ) {

        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomePage(navController = navController)
            }

            composable("colors") {
                ColorsPage(navController = navController)
            }

            composable("cards") {
                CardsPage(navController = navController)
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
        }
    }
}
