package tv.compose.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ColorsPage(navController: NavController) {

    val colors = mapOf<String, Color>(
        "primary" to MaterialTheme.colorScheme.primary,
        "secondary" to MaterialTheme.colorScheme.secondary,
        "tertiary" to MaterialTheme.colorScheme.tertiary,
        "surface" to MaterialTheme.colorScheme.surface,
        "inverseSurface" to MaterialTheme.colorScheme.inverseSurface,
        "background" to MaterialTheme.colorScheme.background,
        "error" to MaterialTheme.colorScheme.error,
        "errorContainer" to MaterialTheme.colorScheme.errorContainer,
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(40.dp)
    ) {
        colors.forEach { (key, value) ->
            item {
                Box(
                    modifier = Modifier
                        .background(value)
                        .height(40.dp)
                        .width(320.dp),
                    contentAlignment = Alignment.Center,
                    propagateMinConstraints = false
                ) {
                    Text(
                        text = key.toString(),
                        color = MaterialTheme.colorScheme.contentColorFor(value)
                    )
                }
            }
        }
    }

}

