package me.rikinmarfatia.hydrohomie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.core.drawShadow
import androidx.ui.core.setContent
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.drawBorder
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.Spacer
import androidx.ui.layout.Stack
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.height
import androidx.ui.layout.padding
import androidx.ui.layout.size
import androidx.ui.layout.width
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.Dp
import androidx.ui.unit.dp
import me.rikinmarfatia.hydrohomie.theme.HydroHomieTheme
import me.rikinmarfatia.hydrohomie.theme.hydroBlue

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HydroHomieTheme {
                WaterContainer()
            }
        }
    }
}

data class WaterState(
    val goal: Int = 8,
    val count: Int = 0
)

@Composable
fun WaterContainer() {
    var waterState by state { WaterState() }
    val addWaterAction: () -> Unit = {
        val count = minOf(waterState.count + 1, waterState.goal)
        waterState = waterState.copy(count = count)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        gravity = ContentGravity.Center,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalGravity = Alignment.CenterHorizontally
        ) {
            ProfilePic()
            Spacer(modifier = Modifier.height(16.dp))
            WaterBackground(waterState)
            Row {
                Button(
                    onClick = addWaterAction,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 8.dp)
                        .clip(MaterialTheme.shapes.large),
                    backgroundColor = hydroBlue
                ) {
                    Text(text = "Add", color = MaterialTheme.colors.onSurface)
                }
                Button(
                    onClick = { waterState = waterState.copy(count = 0) },
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 8.dp)
                        .clip(MaterialTheme.shapes.large),
                    backgroundColor = Color.Red
                ) {
                    Text(text = "Reset", color = MaterialTheme.colors.onSurface)
                }
            }
            GoalDisplay(waterState = waterState)
        }
    }
}

@Composable
fun WaterBackground(state: WaterState) {
    val width = 300.dp
    val height = 400.dp
    val shapeModifier = Modifier
        .clip(
            RoundedCornerShape(16.dp)
        )

    fun waterHeight(): Dp {
        val completion = state.count.toFloat() / state.goal
        return height * completion
    }

    Stack(modifier = Modifier
        .width(width)
        .height(height)
    ) {
        Box(
            modifier = shapeModifier.fillMaxSize(),
            backgroundColor = Color.LightGray
        )
        Box(
            modifier = shapeModifier
                .fillMaxWidth()
                .height(waterHeight())
                .gravity(Alignment.BottomCenter),
            backgroundColor = hydroBlue
        )
    }
}

@Composable
fun GoalDisplay(waterState: WaterState) {

    fun display(state: WaterState): String {
        val cupsLeft = waterState.goal - waterState.count
        return when {
            cupsLeft > 0 -> "${waterState.count} / ${waterState.goal}"
            else -> "Done"
        }
    }

    Text(
        text = display(waterState),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h4,
        color = MaterialTheme.colors.onSurface
    )
}

@Composable
fun ProfilePic() {
    val image = imageResource(id = R.drawable.dummy_image)
    val imageModifier = Modifier
        .size(60.dp)
        .drawShadow(8.dp, CircleShape)
        .drawBorder(4.dp, hydroBlue, CircleShape)


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            asset = image,
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LightPreview() {
    HydroHomieTheme {
        WaterContainer()
    }
}

@Preview(showBackground = true)
@Composable
fun DarkPreview() {
    HydroHomieTheme(darkTheme = true) {
        WaterContainer()
    }
}