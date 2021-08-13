package com.artyomefimov.expensescontrol.infrastructure

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface NotificationManager {

    fun showNotification(
        @StringRes titleResId: Int,
        @StringRes contentResId: Int,
        @DrawableRes iconResId: Int,
    )

    fun hasNoActiveNotifications(): Boolean
}
