package me.rikinmarfatia.hydrohomie

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Providers
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.setContent
import androidx.ui.tooling.preview.Preview
import com.github.zsoltk.compose.backpress.AmbientBackPressHandler
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.router.Router
import me.rikinmarfatia.hydrohomie.domain.WaterState
import me.rikinmarfatia.hydrohomie.domain.WaterViewModel
import me.rikinmarfatia.hydrohomie.theme.HydroHomieTheme
import me.rikinmarfatia.hydrohomie.ui.DailyIntakeContainer
import me.rikinmarfatia.hydrohomie.ui.HistoryContainer
import me.rikinmarfatia.hydrohomie.ui.HistoryState
import me.rikinmarfatia.hydrohomie.ui.Routing

class MainActivity : AppCompatActivity() {
    private val backPressHandler = BackPressHandler()

    private lateinit var waterViewModel: WaterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        waterViewModel = WaterViewModel(
            store = (application as HydroApplication).waterStore,
            broadcaster = (application as HydroApplication).waterBroadcaster
        )

        setContent {
            HydroHomieTheme {
                Providers(
                    AmbientBackPressHandler provides backPressHandler
                ) {
                    var waterState by state { waterViewModel.state }
                    waterViewModel.states().subscribe {
                        Log.d("HydroHomie", it.toString())
                        waterState = it
                    }

                    Router<Routing>("MainActivity", defaultRouting = Routing.Daily) { backStack ->
                        when (backStack.last()) {
                            is Routing.Daily -> {
                                DailyIntakeContainer(
                                    state = waterState,
                                    actions = waterViewModel::actions
                                )
                            }
                            is Routing.History -> HistoryContainer(
                                state = HistoryState(
                                    days = listOf(
                                        waterState
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!backPressHandler.handle()) {
            super.onBackPressed()
        }
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