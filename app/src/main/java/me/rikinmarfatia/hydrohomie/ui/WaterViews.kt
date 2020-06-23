package me.rikinmarfatia.hydrohomie.ui

import androidx.animation.FastOutSlowInEasing
import androidx.animation.FloatPropKey
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.ui.animation.Transition
import androidx.ui.core.Alignment
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.core.drawShadow
import androidx.ui.foundation.Box
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.drawBorder
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Row
import androidx.ui.layout.Stack
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.height
import androidx.ui.layout.size
import androidx.ui.layout.width
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.Dp
import androidx.ui.unit.dp
import me.rikinmarfatia.hydrohomie.R
import me.rikinmarfatia.hydrohomie.models.WaterState
import me.rikinmarfatia.hydrohomie.theme.hydroBlue

private val waterHeightPercent = FloatPropKey()

fun waterTransitionDefinition(waterState: WaterState) = transitionDefinition {
    state("start") { this[waterHeightPercent] = waterState.transition.previous }
    state("end") { this[waterHeightPercent] = waterState.transition.current }

    transition(fromState = "start", toState = "end") {
        waterHeightPercent using tween<Float> {
            duration = 300
            easing = FastOutSlowInEasing
        }
    }
}

@Composable
fun WaterBox(state: WaterState) {
    val width = 300.dp
    val height = 400.dp
    val transitionDef = waterTransitionDefinition(state)
    val shapeModifier = Modifier
        .clip(
            RoundedCornerShape(16.dp)
        )

    fun waterHeight(completion: Float): Dp {
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
        Transition(definition = transitionDef, initState = "start", toState = "end") { state ->
            Box(
                modifier = shapeModifier
                    .fillMaxWidth()
                    .height(waterHeight(state[waterHeightPercent]))
                    .gravity(Alignment.BottomCenter),
                backgroundColor = hydroBlue
            )
        }
    }
}

@Composable
fun DailyGoalDisplay(waterState: WaterState) {

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

@Preview
@Composable
fun WaterBoxPreview() {
    WaterBox(state = WaterState())
}

@Preview(showBackground = true)
@Composable
fun DailyGoalDisplayPreview() {
    DailyGoalDisplay(waterState = WaterState(count = 4))
}

@Preview
@Composable
fun ProfilePicPreview() {
    ProfilePic()
}
