package tv.compose.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tv.compose.ui.components.TvCard

@Composable
fun CardsPage(navController: NavController) {
    Column(modifier = Modifier.padding(0.dp, 40.dp)) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(58.dp, 20.dp)
        ) {
            item { TvCard() {} }
            item { TvCard() {} }
            item { TvCard() {} }
            item { TvCard() {} }
            item { TvCard() {} }
            item { TvCard() {} }
        }

    }
}