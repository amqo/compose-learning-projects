package com.amqo.locationtrackdemo

import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService: Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startUpdates()
            ACTION_STOP -> stopUpdates()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startUpdates() {

        val resultIntent = Intent(this, MainActivity::class.java)
        val resultPendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val notification = NotificationCompat.Builder(this, "location")
            .setContentIntent(resultPendingIntent)
            .setContentTitle("Tracking location...")
            .setContentText("Location: pending")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)

        locationClient
            .getLocationUpdates(10_000L)
            .catch { error -> error.printStackTrace() }
            .onEach { location ->
                val lat = "%.4f".format(location.latitude)
                val long = "%.4f".format(location.longitude)
                val updatedNotification = notification.setContentText(
                    "Location: ($lat, $long)"
                )
                with (NotificationManagerCompat.from(this)) {
                    notify(1, updatedNotification.build())
                }
            }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun stopUpdates() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}