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
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.Spacer
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.height
import androidx.ui.layout.padding
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import me.rikinmarfatia.hydrohomie.models.WaterState
import me.rikinmarfatia.hydrohomie.models.WaterTransition
import me.rikinmarfatia.hydrohomie.theme.HydroHomieTheme
import me.rikinmarfatia.hydrohomie.theme.hydroBlue
import me.rikinmarfatia.hydrohomie.ui.DailyGoalDisplay
import me.rikinmarfatia.hydrohomie.ui.ProfilePic
import me.rikinmarfatia.hydrohomie.ui.WaterGlass

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HydroHomieTheme {
                DailyTrackerContainer()
            }
        }
    }
}

@Composable
fun DailyTrackerContainer() {
    // State that holds the information needed to render the screen
    var waterState by state { WaterState() }

    // Essentially a state reducer, updates the state based on this action
    val addWaterAction: () -> Unit = {
        val current = waterState.count.toFloat() / waterState.goal
        val count = minOf(waterState.count + 1, waterState.goal)
        val next = count.toFloat() / waterState.goal
        val transition = WaterTransition(current, next)
        waterState = waterState.copy(count = count, transition = transition)
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
            ProfilePic()
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
                    onClick = {
                        waterState = waterState.copy(
                            count = 0,
                            transition = WaterTransition()
                        )
                    },
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


@Preview(showBackground = true)
@Composable
fun LightPreview() {
    HydroHomieTheme {
        DailyTrackerContainer()
    }
}

@Preview(showBackground = true)
@Composable
fun DarkPreview() {
    HydroHomieTheme(darkTheme = true) {
        DailyTrackerContainer()
    }
}