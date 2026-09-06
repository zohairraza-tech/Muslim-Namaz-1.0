package com.example.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.data.preferences.UserPreferences

class NamazAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "namaz_prayer_reminders_channel"
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_PRAYER_TIME = "extra_prayer_time"
        const val EXTRA_CITY = "extra_city"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val prayerName = intent.getStringExtra(EXTRA_PRAYER_NAME) ?: "Prayer"
        val prayerTime = intent.getStringExtra(EXTRA_PRAYER_TIME) ?: ""
        val city = intent.getStringExtra(EXTRA_CITY) ?: "your location"

        val prefs = UserPreferences(context)
        val vibration = prefs.vibrationEnabled

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Prayer Times (Azan Reminders)",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications and Azan alarms for daily Islamic prayers"
                enableVibration(vibration)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val openIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("🕌 Time for $prayerName ($prayerTime)")
            .setContentText("Hayya 'ala-s-Salah! It is time for $prayerName in $city.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (vibration) {
            notificationBuilder.setVibrate(longArrayOf(0, 300, 200, 300))
        }
        notificationBuilder.setSound(defaultSoundUri)

        notificationManager.notify(prayerName.hashCode(), notificationBuilder.build())
    }
}
