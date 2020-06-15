package me.rikinmarfatia.hydrohomie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.core.setContent
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Stack
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.height
import androidx.ui.layout.padding
import androidx.ui.layout.width
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
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
            WaterBackground(waterState)
            Button(
                onClick = addWaterAction,
                modifier = Modifier
                    .padding(16.dp)
                    .clip(MaterialTheme.shapes.large),
                backgroundColor = hydroBlue
            ) {
                Text(text = "Add", color = MaterialTheme.colors.onSurface)
            }
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