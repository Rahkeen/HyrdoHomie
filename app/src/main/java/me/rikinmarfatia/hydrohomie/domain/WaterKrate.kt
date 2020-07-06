package me.rikinmarfatia.hydrohomie.domain

import android.content.Context
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.intPref

class WaterKrate(context: Context, name: String = "WaterPrefs") : SimpleKrate(context, name) {
    var count by intPref("count", 0)
    val goal by intPref("goal", 8)

    fun toWaterState(): WaterState {
        return WaterState(
            count = count,
            goal = goal,
            transition = WaterTransition(
                current = count.toFloat() / goal
            )
        )
    }
}

