package com.artyomefimov.expensescontrol.domain.ext

import android.app.NotificationManager
import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.core.app.NotificationManagerCompat

val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

val Context.notificationManagerCompat: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)

val Context.appWidgetManager: AppWidgetManager
    get() = getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager
