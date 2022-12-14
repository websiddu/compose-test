package tv.compose.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection.Companion.Left
import androidx.compose.ui.focus.FocusDirection.Companion.Right
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tv.compose.ui.focus.FocusableSurface

@Composable
fun HomePage(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background, shape = RectangleShape)
            .padding(58.dp, 40.dp)
            .clip(RectangleShape)
    ) {

        val data = listOf<String>(
            "CrossFade",
            "Typography",
            "Elevation",
            "Colors",
            "Buttons",
            "Cards",
            "FC",
            "VerticalNav",
            "ImmersiveCluster",
            "Tabs",
            "jetstream/home"
        )

        val firstItem = remember { FocusRequester() }
        val firstItemModifier = Modifier.focusRequester(firstItem)

        val coroutineScope = rememberCoroutineScope()
        val state = rememberLazyListState()

        val focusManager = LocalFocusManager.current
        val layoutDirection = LocalLayoutDirection.current

        LaunchedEffect(key1 = Unit) {
            focusManager.moveFocus(if (layoutDirection == LayoutDirection.Ltr) Right else Left)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp),
        ) {
            items(data) { item ->
                FocusableSurface(
                    onPress = {
                        navController.navigate(item)
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.height(120.dp),
                    focusRequester = if (item == "Typography") firstItem else FocusRequester()
                )
                {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item,
                            letterSpacing = 0.1.sp,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        }
    }
}