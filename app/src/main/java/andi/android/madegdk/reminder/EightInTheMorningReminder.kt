package andi.android.madegdk.reminder

import andi.android.madegdk.R
import andi.android.madegdk.ui.home.HomeActivity
import andi.android.madegdk.ui.home.releasetoday.ReleaseTodayActivity
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.*

class EightInTheMorningReminder: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val title = context?.getString(R.string.eight_morning_notif_title)
        val message = context?.getString(R.string.eight_morning_notif_message)
        val notifId = REQUEST_CODE

        //Jika Anda ingin menampilkan dengan Notif anda bisa menghilangkan komentar pada baris dibawah ini.
        showNotification(context, title, message, notifId)
    }

    fun setRepeatingAlarm(context: Context) {

        Log.d("AS", "MASUK")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, EightInTheMorningReminder::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Log.d("STAT", "SET")
    }

    private fun showNotification(context: Context?, title: String?, message: String?, notificationId: Int) {

        val CHANNEL_ID = "CHANNEL_EIGHT"
        val CHANNEL_NAME = "8 in the morning notification"

        val notificationIntent = Intent(context, ReleaseTodayActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context)
                .addParentStack(ReleaseTodayActivity::class.java)
                .addNextIntent(notificationIntent)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManagerCompat = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
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
        val intent = Intent(context, EightInTheMorningReminder::class.java)

        val pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Log.d("STAT", "CANCEL")
    }

    private val REQUEST_CODE = 102

}