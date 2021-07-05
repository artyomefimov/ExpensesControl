package com.artyomefimov.expensescontrol.infrastructure

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.ext.notificationManager
import com.artyomefimov.expensescontrol.domain.ext.notificationManagerCompat
import com.artyomefimov.expensescontrol.presentation.view.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.Clock
import javax.inject.Inject

class NotificationBuilderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val clock: Clock,
): NotificationBuilder {

    private companion object {
        const val REQUEST_CODE = 100
    }

    override fun showNotification(
        titleResId: Int,
        contentResId: Int,
        iconResId: Int
    ) {
        val channelId = context.getString(R.string.notification_channel_id)
        val notificationId = clock.now().toEpochMilliseconds().toInt()
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(iconResId)
            .setContentTitle(context.getString(titleResId))
            .setContentText(context.getString(contentResId))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(createPendingIntent())
            .setAutoCancel(true)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(channelId)
        }

        context.notificationManagerCompat.notify(notificationId, notification)
    }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return PendingIntent.getActivity(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String) {
        val name = context.getString(R.string.notification_channel_name)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance)

        context.notificationManager.createNotificationChannel(channel)
    }
}
