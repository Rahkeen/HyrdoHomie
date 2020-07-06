package me.rikinmarfatia.hydrohomie.effects

import android.content.Context
import android.content.Intent
import me.rikinmarfatia.hydrohomie.HydroWidget

class WaterBroadcaster(private val context: Context) {
    fun sendUpdateBroadcast() {
        context.sendBroadcast(Intent(context, HydroWidget::class.java).apply {
            action = HydroWidget.ACTION_UPDATE_COUNT
        })
    }
}