package tv.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import tv.compose.router.Router
import tv.compose.ui.components.TvButton
import tv.compose.ui.theme.TvComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}


@Composable
fun App() {

    var isDarkTheme by remember { mutableStateOf(false) }

    TvComposeTheme(isDarkTheme) {
        val navController = rememberNavController()

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            Row(modifier = Modifier
                .padding(6.dp)
                .scale(0.5f)) {
                TvButton(
                    onPress = {
                        isDarkTheme = !isDarkTheme
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Dark theme: $isDarkTheme")
                }
            }

            Router(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
}