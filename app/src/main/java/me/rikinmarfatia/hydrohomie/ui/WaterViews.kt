package me.rikinmarfatia.hydrohomie.ui

import androidx.animation.FastOutSlowInEasing
import androidx.animation.FloatPropKey
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.emptyContent
import androidx.ui.animation.Transition
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.foundation.Box
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
import androidx.ui.material.Surface
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.Dp
import androidx.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
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

/**
 * A view that represents a water glass that fills as you drink more water
 * and get closer to your goal.
 */
@Composable
fun WaterGlass(state: WaterState) {
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

/**
 * A simple textual display showing the current count over the goal
 */
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

/**
 * A profile picture view. Right now it just displays a dummy image.
 */
@Composable
fun ProfilePic() {
    val imageModifier = Modifier
        .size(60.dp)
        .clip(CircleShape)
        .drawBorder(size = 2.dp, color = MaterialTheme.colors.onSurface, shape = CircleShape)

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
fun WaterBoxPreview() {
    WaterGlass(state = WaterState())
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
