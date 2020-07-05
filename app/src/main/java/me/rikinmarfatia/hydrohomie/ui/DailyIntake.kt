package me.rikinmarfatia.hydrohomie.ui

import android.content.Context
import android.content.Intent
import androidx.animation.FastOutSlowInEasing
import androidx.animation.FloatPropKey
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
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
import me.rikinmarfatia.hydrohomie.HydroWidget
import me.rikinmarfatia.hydrohomie.models.WaterKrate
import me.rikinmarfatia.hydrohomie.models.WaterState
import me.rikinmarfatia.hydrohomie.models.WaterTransition
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
    context: Context,
    store: WaterKrate,
    onProfileClicked: () -> Unit = {}
) {
    // State that holds the information needed to render the screen
    var waterState by state {
        WaterState(
            count = store.count,
            goal = store.goal,
            transition = WaterTransition(
                current = store.count.toFloat() / store.goal
            )
        )
    }

    // Essentially a state reducer, updates the state based on this action
    val addWaterAction: () -> Unit = {
        val current = waterState.count.toFloat() / waterState.goal
        val count = minOf(waterState.count + 1, waterState.goal)
        val next = count.toFloat() / waterState.goal
        val transition = WaterTransition(current, next)
        waterState = waterState.copy(count = count, transition = transition)
        store.count = count
        context.sendBroadcast(Intent(context, HydroWidget::class.java).apply {
            action = HydroWidget.ACTION_UPDATE_COUNT
        })
    }

    val resetAction: () -> Unit = {
        waterState = WaterState()
        store.count = 0
        context.sendBroadcast(Intent(context, HydroWidget::class.java).apply {
            action = HydroWidget.ACTION_UPDATE_COUNT
        })
    }

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
            ProfilePic(onProfileClicked)
            Spacer(modifier = Modifier.height(16.dp))
            WaterGlass(waterState)
            // Container that lays out children horizontally
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
                    onClick = resetAction,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 8.dp)
                        .clip(MaterialTheme.shapes.large),
                    backgroundColor = Color.Red
                ) {
                    Text(text = "Reset", color = MaterialTheme.colors.onSurface)
                }
            }
            DailyGoalDisplay(waterState = waterState)
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
