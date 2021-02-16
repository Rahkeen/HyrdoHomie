package me.rikinmarfatia.hydrohomie.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.rikinmarfatia.hydrohomie.domain.WaterAction
import me.rikinmarfatia.hydrohomie.domain.WaterState
import me.rikinmarfatia.hydrohomie.domain.WaterTransition
import me.rikinmarfatia.hydrohomie.theme.hydroBlue

@Composable
fun DailyIntakeContainer(
    state: WaterState,
    actions: (WaterAction) -> Unit
) {
    // A very basic container, really easy to use to center everything
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        WaterGlass(state)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePic()
            Spacer(modifier = Modifier.height(16.dp))
            ActionButtons(actions)
            DailyGoalDisplay(waterState = state)
        }
    }
}

@Composable
private fun ActionButtons(actions: (WaterAction) -> Unit) {
    Row {
        Button(
            onClick = { actions(WaterAction.Add(1)) },
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .shadow(elevation = 4.dp, shape = CircleShape),
            colors = buttonColors(backgroundColor = hydroBlue)
        ) {
            Text(text = "Add", color = MaterialTheme.colors.onSurface)
        }
        Button(
            onClick = { actions(WaterAction.Reset) },
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .shadow(elevation = 4.dp, shape = CircleShape),
            colors = buttonColors(backgroundColor = Color.Red)
        ) {
            Text(text = "Reset", color = MaterialTheme.colors.onSurface)
        }
    }
}

/**
 * A view that represents a water glass that fills as you drink more water
 * and get closer to your goal.
 */
@Composable
fun WaterGlass(state: WaterState) {

    fun waterShape(state: WaterState): Shape {
        val cupsLeft = state.goal - state.count
        return when {
            cupsLeft > 0 -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            else -> RectangleShape
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .clip(waterShape(state))
                .fillMaxWidth()
                .fillMaxHeight(fraction = state.percentCompletion)
                .background(color = hydroBlue),
        )
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
