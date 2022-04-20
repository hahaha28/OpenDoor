package `fun`.inaction.opendoor

import `fun`.inaction.opendoor.App.Companion.context
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        findViewById<Button>(R.id.openBtn).setOnClickListener {
            OpenUtil.openDoor { b, s ->
                runOnUiThread {
                    resultTextView.text = s
                }
            }
        }

        findViewById<Button>(R.id.addWidgetBtn).setOnClickListener {
            addWidget()
        }
    }

    fun addWidget(){
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           val appWidgetManager =  getSystemService(AppWidgetManager::class.java)
           val myProvider = ComponentName(this, NewAppWidget::class.java)

           val successCallback: PendingIntent? = if (appWidgetManager.isRequestPinAppWidgetSupported) {
               // Create the PendingIntent object only if your app needs to be notified
               // that the user allowed the widget to be pinned. Note that, if the pinning
               // operation fails, your app isn't notified.
               Intent().let { intent ->
                   // Configure the intent so that your app's broadcast receiver gets
                   // the callback successfully. This callback receives the ID of the
                   // newly-pinned widget (EXTRA_APPWIDGET_ID).
                   PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
               }
           } else {
               null
           }

           successCallback?.also { pendingIntent ->
               appWidgetManager.requestPinAppWidget(myProvider, null, pendingIntent)
           }
        }
    }
}