package com.artyomefimov.expensescontrol.infrastructure

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.snackbar.Snackbar

val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

val Context.notificationManagerCompat: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)

fun Activity.hideKeyboard() = currentFocus?.let {
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(it.windowToken, 0)
    it.clearFocus()
}

fun View.showSnackbar(
    @StringRes messageResId: Int,
) = Snackbar.make(
    this,
    context.getString(messageResId),
    Snackbar.LENGTH_SHORT,
).show()
