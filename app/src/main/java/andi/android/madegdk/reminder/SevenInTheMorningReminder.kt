package andi.android.madegdk.reminder

import andi.android.madegdk.R
import andi.android.madegdk.ui.home.HomeActivity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.*


class SevenInTheMorningReminder : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val title = context?.getString(R.string.seven_morning_notif_title)
        val message = context?.getString(R.string.seven_morning_notif_message)
        val notifId = REQUEST_CODE

        showNotification(context, title, message, notifId)
    }

    fun setRepeatingAlarm(context: Context) {

        Log.d("AS", "MASUK")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, SevenInTheMorningReminder::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

    }

    private fun showNotification(context: Context?, title: String?, message: String?, notificationId: Int) {

        val CHANNEL_ID = "CHANNEL_SEVEN"
        val CHANNEL_NAME = "7 in the morning notification"

        val notificationIntent = Intent(context, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        val notificationManagerCompat = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notificationId, notification)

    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, SevenInTheMorningReminder::class.java)

        val pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
    }

    fun isAlarmSet(context: Context): Boolean {
        val intent = Intent(context, SevenInTheMorningReminder::class.java)

        return PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_NO_CREATE) != null
    }

    private val REQUEST_CODE = 101

}