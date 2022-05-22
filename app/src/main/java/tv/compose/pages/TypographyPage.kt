package tv.compose.pages;

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun TypographyPage() {

    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onBackground) {
        Row(
            modifier = Modifier
                .size(width = 960.dp, height = 540.dp)
                .padding(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {


            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(text = "Display Large", style = MaterialTheme.typography.displayLarge)
                Text(text = "Display Medium", style = MaterialTheme.typography.displayMedium)
                Text(text = "Display Small", style = MaterialTheme.typography.displaySmall)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Headline Large", style = MaterialTheme.typography.headlineLarge)
                Text(text = "Headline Medium", style = MaterialTheme.typography.headlineMedium)
                Text(text = "Headline Small", style = MaterialTheme.typography.headlineSmall)
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Title large", style = MaterialTheme.typography.titleLarge)
                Text(text = "Title Medium", style = MaterialTheme.typography.titleMedium)
                Text(text = "Title Small", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Label Large", style = MaterialTheme.typography.labelLarge)
                Text(text = "Label Medium", style = MaterialTheme.typography.labelMedium)
                Text(text = "Label Small", style = MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Body Large", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Body Medium", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Body small", style = MaterialTheme.typography.bodySmall)
            }
        }

    }
}