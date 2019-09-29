package andi.android.madegdk.ui.home.setting

import andi.android.madegdk.R
import andi.android.madegdk.reminder.EightInTheMorningReminder
import andi.android.madegdk.reminder.SevenInTheMorningReminder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val dailyReminder = SevenInTheMorningReminder()
        dailyReminderSwitch.isChecked = dailyReminder.isAlarmSet(this)

        val releaseReminder = EightInTheMorningReminder()
        releaseTodaySwitch.isChecked = releaseReminder.isAlarmSet(this)

        dailyReminderSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                dailyReminder.setRepeatingAlarm(this)
            } else {
                dailyReminder.cancelAlarm(this)
            }
        }

        releaseTodaySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                releaseReminder.setRepeatingAlarm(this)
            } else {
                releaseReminder.cancelAlarm(this)
            }
        }
    }
}
