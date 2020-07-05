package me.rikinmarfatia.hydrohomie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Providers
import androidx.ui.core.ContextAmbient
import androidx.ui.core.setContent
import androidx.ui.tooling.preview.Preview
import com.github.zsoltk.compose.backpress.AmbientBackPressHandler
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.router.Router
import me.rikinmarfatia.hydrohomie.models.WaterKrate
import me.rikinmarfatia.hydrohomie.theme.HydroHomieTheme
import me.rikinmarfatia.hydrohomie.ui.DailyIntakeContainer
import me.rikinmarfatia.hydrohomie.ui.HistoryContainer
import me.rikinmarfatia.hydrohomie.ui.HistoryState
import me.rikinmarfatia.hydrohomie.ui.Routing

class MainActivity : AppCompatActivity() {
    private lateinit var waterStore: WaterKrate
    private val backPressHandler = BackPressHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        waterStore = WaterKrate(this)

        setContent {
            HydroHomieTheme {
                Providers(
                    AmbientBackPressHandler provides backPressHandler
                ) {
                    Router<Routing>("MainActivity", defaultRouting = Routing.Daily) { backStack ->
                        when (backStack.last()) {
                            is Routing.Daily -> DailyIntakeContainer(
                                context = this,
                                store = waterStore
                            ) {
                                backStack.push(Routing.History)
                            }
                            is Routing.History -> HistoryContainer(
                                state = HistoryState(
                                    days = listOf(
                                        waterStore.toWaterState()
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
            context = ContextAmbient.current,
            store = WaterKrate(ContextAmbient.current)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DarkPreview() {
    HydroHomieTheme(darkTheme = true) {
        DailyIntakeContainer(
            context = ContextAmbient.current,
            store = WaterKrate(ContextAmbient.current)
        )
    }
}