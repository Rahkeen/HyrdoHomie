package me.rikinmarfatia.hydrohomie

import android.app.Application
import me.rikinmarfatia.hydrohomie.domain.WaterKrate
import me.rikinmarfatia.hydrohomie.effects.WaterBroadcaster

class HydroApplication : Application() {

    lateinit var waterStore: WaterKrate
        private set
    lateinit var waterBroadcaster: WaterBroadcaster
        private set

    override fun onCreate() {
        super.onCreate()

        waterStore = WaterKrate(applicationContext)
        waterBroadcaster = WaterBroadcaster(applicationContext)
    }
}