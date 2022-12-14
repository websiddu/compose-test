package tv.compose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tv.compose.ui.focus.FocusableSurface
import tv.compose.ui.focus.SurfaceStyle

@Composable
fun ListItem(onPress: () -> Unit, label: String = "", icon: ImageVector, hideLabel: Boolean = false) {
  FocusableSurface(
    onPress = onPress,
    shape = CircleShape,
    style = SurfaceStyle(
      backgroundColor = Color.Unspecified
    ),
    focusStyle = SurfaceStyle(
      scale = 1.025f,
      backgroundColor = MaterialTheme.colorScheme.secondaryContainer
    )
  ) {
    Row(
      modifier = Modifier
        .padding(16.dp, 8.dp)
        .height(24.dp)
        .fillMaxWidth(1f)
    ) {
      Icon(
        icon, label,
        tint = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier.size(24.dp)
      )
      if (!hideLabel) {
        Spacer(modifier = Modifier.size(12.dp))
        Text(
          text = label,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.align(Alignment.CenterVertically),
          style = MaterialTheme.typography.labelLarge,
          maxLines = 1
        )
      }
    }
  }
}