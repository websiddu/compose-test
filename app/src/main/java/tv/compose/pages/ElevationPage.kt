package tv.compose.pages;


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tv.compose.ui.focus.FocusableSurface
import tv.compose.ui.focus.SurfaceStyle
import tv.compose.ui.theme.elevation

@Composable
fun ElevationPage() {
    
    
    val fm = LocalFocusManager.current
    
    val allItems = listOf(
        MaterialTheme.elevation.Level0,
        MaterialTheme.elevation.Level1,
        MaterialTheme.elevation.Level2,
        MaterialTheme.elevation.Level3,
        MaterialTheme.elevation.Level4,
        MaterialTheme.elevation.Level5
    )

    val elements = listOf(1.dp, 2.dp, 4.dp, 6.dp, 8.dp, 10.dp);

    Column(
        modifier = Modifier
            .size(width = 960.dp, height = 540.dp)
            .padding(40.dp)
            .clip(Shapes.None),
    ) {

        LazyVerticalGrid(
            modifier = Modifier.clip(Shapes.None).onPlaced {
                fm.moveFocus(FocusDirection.Down)
            },
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(allItems) { item ->
                FocusableSurface(
                    onPress = { /*TODO*/ },
                    modifier = Modifier
                        .size(120.dp, 90.dp),
                    shape = RoundedCornerShape(20.dp),
                    style = SurfaceStyle(
                        tonalElevation = item,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        backgroundColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Surface $item", textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(elements) { item ->

                FocusableSurface(
                    onPress = { /*TODO*/ },
                    modifier = Modifier
                        .size(120.dp, 90.dp),
                    shape = RoundedCornerShape(20.dp),
                    style = SurfaceStyle(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = MaterialTheme.elevation.Level3,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        shadowElevation = item
                    )
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Shadow $item", textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

}