package tv.compose.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tv.compose.ui.components.TvButton
import tv.compose.ui.components.TvCard
import tv.compose.ui.components.TvIconButton

@Composable
fun ButtonsPage(navController: NavController) {
    Column(modifier = Modifier.padding(40.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        TvButton(onPress = {
            Log.e("my-log", "Clicked...")

        }) {
            Text("My button")
        }

        Row(modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 0.dp)) {
            Text(
                text = "Icon Button",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TvIconButton(onPress = { /*TODO*/ }, icon = Icons.Default.Add, size = 64.dp) {}
            TvIconButton(onPress = { /*TODO*/ }, icon = Icons.Default.Add, size = 52.dp) {}
            TvIconButton(onPress = { /*TODO*/ }, icon = Icons.Default.Add, size = 40.dp) {}
            TvIconButton(onPress = { /*TODO*/ }, icon = Icons.Default.Add, size = 32.dp) {}
            TvIconButton(onPress = { /*TODO*/ }, icon = Icons.Default.Add, size = 24.dp) {}
            TvIconButton(onPress = { /*TODO*/ }, icon = Icons.Default.Add, size = 16.dp) {}
        }


    }
}