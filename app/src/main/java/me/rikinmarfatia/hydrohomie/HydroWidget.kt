package me.rikinmarfatia.hydrohomie

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import me.rikinmarfatia.hydrohomie.models.WaterKrate

class HydroWidget: AppWidgetProvider() {

    private companion object {
        const val TAG = "HydroHomieWidget"
        const val ACTION_UPDATE_COUNT = "ACTION_UPDATE_COUNT"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d(TAG, "Updated")
        appWidgetIds.forEach { appWidgetId ->
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d(TAG, "Received: ${intent.action}")

        val waterStore = WaterKrate(context)
        if (ACTION_UPDATE_COUNT == intent.action ) {
            waterStore.count++

            val display = String.format(
                context.resources.getString(R.string.widget_count_display),
                waterStore.count,
                waterStore.goal
            )

            Log.d(TAG, display)

            val views: RemoteViews = RemoteViews(
                context.packageName,
                R.layout.hydro_widget
            ).apply {
                setTextViewText(R.id.widget_goal_display, display)
            }

            val appWidget = ComponentName(context, HydroWidget::class.java)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            appWidgetManager.updateAppWidget(appWidget, views)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val waterStore = WaterKrate(context)
        Log.d(TAG, "WaterStore Count: ${waterStore.count}")

        val pendingIntent: PendingIntent = Intent(context, HydroWidget::class.java)
            .let { intent ->
                intent.action = ACTION_UPDATE_COUNT
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }

        val display = String.format(
            context.resources.getString(R.string.widget_count_display),
            waterStore.count,
            waterStore.goal
        )

        val views: RemoteViews = RemoteViews(
            context.packageName,
            R.layout.hydro_widget
        ).apply {
            setOnClickPendingIntent(R.id.widget_add_button, pendingIntent)
            setTextViewText(R.id.widget_goal_display, display)
        }

        // Tell the AppWidgetManager to perform an update on the current app widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
