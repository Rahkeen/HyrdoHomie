package me.rikinmarfatia.hydrohomie.models

import android.content.Context
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.intPref

class WaterKrate(context: Context) : SimpleKrate(context) {
    var count by intPref("count", 0)
    val goal by intPref("goal", 8)
}