package me.rikinmarfatia.hydrohomie

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import me.rikinmarfatia.hydrohomie.domain.WaterState
import me.rikinmarfatia.hydrohomie.domain.WaterViewModel
import me.rikinmarfatia.hydrohomie.theme.HydroHomieTheme
import me.rikinmarfatia.hydrohomie.ui.DailyIntakeContainer

class MainActivity : AppCompatActivity() {

    private lateinit var waterViewModel: WaterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        waterViewModel = WaterViewModel(
            store = (application as HydroApplication).waterStore,
            broadcaster = (application as HydroApplication).waterBroadcaster
        )

        setContent {
            HydroHomieTheme {
                var waterState by remember { mutableStateOf(waterViewModel.state) }
                waterViewModel.states().subscribe {
                    Log.d("HydroHomie", it.toString())
                    waterState = it
                }

                DailyIntakeContainer(
                    state = waterState,
                    actions = waterViewModel::actions
                )

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}

@Preview(showBackground = true)
@Composable
fun LightPreview() {
    HydroHomieTheme(darkTheme = false) {
        DailyIntakeContainer(
            state = WaterState(),
            actions = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DarkPreview() {
    HydroHomieTheme(darkTheme = true) {
        DailyIntakeContainer(
            state = WaterState(),
            actions = {}
        )
    }
}