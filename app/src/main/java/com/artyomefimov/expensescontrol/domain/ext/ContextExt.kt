package com.artyomefimov.expensescontrol.domain.ext

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val PREFERENCES_FILE_NAME = "expenses-control-preferences"

val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

val Context.notificationManagerCompat: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)
// todo миграции
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_FILE_NAME)
