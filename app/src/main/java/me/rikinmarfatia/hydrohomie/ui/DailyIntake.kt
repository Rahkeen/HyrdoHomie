package me.rikinmarfatia.hydrohomie.ui

import androidx.animation.FastOutSlowInEasing
import androidx.animation.FloatPropKey
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.ui.animation.Transition
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.Spacer
import androidx.ui.layout.Stack
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.height
import androidx.ui.layout.padding
import androidx.ui.layout.width
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.Dp
import androidx.ui.unit.dp
import me.rikinmarfatia.hydrohomie.domain.WaterAction
import me.rikinmarfatia.hydrohomie.domain.WaterState
import me.rikinmarfatia.hydrohomie.domain.WaterTransition
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
fun DailyIntakeContainer(
    state: WaterState,
    actions: (WaterAction) -> Unit
) {

    // A very basic container, really easy to use to center everything
    Box(
        modifier = Modifier.fillMaxSize(),
        gravity = ContentGravity.Center,
        backgroundColor = MaterialTheme.colors.background
    ) {
        // Container that layouts children vertically
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalGravity = Alignment.CenterHorizontally
        ) {
            ProfilePic()
            Spacer(modifier = Modifier.height(16.dp))
            WaterGlass(state)
            // Container that lays out children horizontally
            Row {
                Button(
                    onClick = { actions(WaterAction.Add(1)) },
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 8.dp)
                        .clip(MaterialTheme.shapes.large),
                    backgroundColor = hydroBlue
                ) {
                    Text(text = "Add", color = MaterialTheme.colors.onSurface)
                }
                Button(
                    onClick = { actions(WaterAction.Reset) },
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 8.dp)
                        .clip(MaterialTheme.shapes.large),
                    backgroundColor = Color.Red
                ) {
                    Text(text = "Reset", color = MaterialTheme.colors.onSurface)
                }
            }
            DailyGoalDisplay(waterState = state)
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
    val shapeModifier = Modifier.clip(RoundedCornerShape(16.dp))

    fun waterHeight(completion: Float): Dp {
        return height * completion
    }

    Stack(
        modifier = Modifier
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
        val cupsLeft = state.goal - state.count
        return when {
            cupsLeft > 0 -> "${state.count} / ${state.goal}"
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

@Preview
@Composable
fun WaterBoxPreview() {
    WaterGlass(
        state = WaterState(
            count = 4,
            goal = 8,
            transition = WaterTransition(
                previous = .375F,
                current = .5F
            )
        )
    )
}

@Preview
@Composable
fun DailyGoalDisplayPreview() {
    DailyGoalDisplay(waterState = WaterState(count = 4))
}
