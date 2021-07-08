package com.artyomefimov.expensescontrol.domain.ext

import android.app.NotificationManager
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import androidx.core.app.NotificationManagerCompat
import com.artyomefimov.expensescontrol.infrastructure.widget.ExpensesControlAppWidget

val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

val Context.notificationManagerCompat: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)

val Context.appWidgetManager: AppWidgetManager
    get() = getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager

val Context.inputMethodManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun Context.updateWidget() {
    Intent(this, ExpensesControlAppWidget::class.java)
        .apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, getAppWidgetIds())
        }
        .also { sendBroadcast(it) }
}

fun Context.getAppWidgetIds(): IntArray = appWidgetManager.getAppWidgetIds(
    ComponentName(this, ExpensesControlAppWidget::class.java)
)