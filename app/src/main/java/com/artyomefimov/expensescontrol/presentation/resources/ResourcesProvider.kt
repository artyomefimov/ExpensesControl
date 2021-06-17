package com.artyomefimov.expensescontrol.presentation.resources

import androidx.annotation.StringRes

interface ResourcesProvider {

    fun getString(@StringRes resId: Int): String
}
