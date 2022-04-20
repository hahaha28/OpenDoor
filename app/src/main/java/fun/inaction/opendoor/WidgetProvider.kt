package `fun`.inaction.opendoor

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast

class WidgetProvider : AppWidgetProvider() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            Toast.makeText(it,"onReceive",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appWidgetIds?.forEach {
            val remoteView = RemoteViews(context?.packageName, R.layout.view_widget)

            val pi = PendingIntent.getBroadcast(context,1,Intent(),0)
            remoteView.setOnClickPendingIntent(R.id.button,pi)

            appWidgetManager?.updateAppWidget(it, remoteView)
        }

    }
}