package com.artyomefimov.expensescontrol.presentation.resources

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

interface ResourcesProvider {

    fun getString(@StringRes resId: Int): String

    fun getStringArray(@ArrayRes resId: Int): Array<String>
}
