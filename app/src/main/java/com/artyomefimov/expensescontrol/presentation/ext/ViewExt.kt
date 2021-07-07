package com.artyomefimov.expensescontrol.presentation.ext

import android.view.View
import androidx.annotation.StringRes
import com.artyomefimov.expensescontrol.domain.ext.inputMethodManager
import com.google.android.material.snackbar.Snackbar

fun View.hideKeyboard() = context.inputMethodManager.hideSoftInputFromWindow(windowToken, 0)

fun View.showSnackbar(
    @StringRes messageResId: Int,
) = Snackbar.make(
    this,
    context.getString(messageResId),
    Snackbar.LENGTH_SHORT,
).show()
