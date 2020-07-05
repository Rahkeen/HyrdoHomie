package me.rikinmarfatia.hydrohomie.ui

import androidx.compose.Composable
import androidx.compose.emptyContent
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.foundation.clickable
import androidx.ui.foundation.drawBorder
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.size
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage

/**
 * A profile picture view. Right now it just displays a dummy image.
 */
@Composable
fun ProfilePic(onClick: () -> Unit = {}) {
    val imageModifier = Modifier
        .size(60.dp)
        .clip(CircleShape)
        .drawBorder(size = 2.dp, color = MaterialTheme.colors.onSurface, shape = CircleShape)
        .clickable(onClick = onClick)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        CoilImage(
            data = "https://i.pinimg.com/originals/13/8c/8d/138c8d92efa0228c42c5e43517d99479.jpg",
            modifier = imageModifier,
            loading = {
                Surface(
                    color = Color.LightGray,
                    content = emptyContent(),
                    modifier = Modifier.fillMaxSize()
                )
            }
        )
    }
}


@Preview
@Composable
fun ProfilePicPreview() {
    ProfilePic()
}
