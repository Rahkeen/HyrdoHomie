package me.rikinmarfatia.hydrohomie.ui


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage

/**
 * A profile picture view. Right now it just displays a dummy image.
 */
@Composable
fun ProfilePic(onClick: () -> Unit = {}) {
    val imageModifier = Modifier
        .size(60.dp)
        .clip(CircleShape)
        .border(width = 4.dp, color = MaterialTheme.colors.onSurface, shape = CircleShape)
        .clickable(onClick = onClick)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        CoilImage(
            data = "https://i.pinimg.com/originals/13/8c/8d/138c8d92efa0228c42c5e43517d99479.jpg",
            contentDescription = null,
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
    }
}


@Preview
@Composable
fun ProfilePicPreview() {
    ProfilePic()
}
