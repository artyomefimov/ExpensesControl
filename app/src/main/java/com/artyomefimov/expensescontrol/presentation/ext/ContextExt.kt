package com.artyomefimov.expensescontrol.presentation.ext

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() = currentFocus?.let {
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(it.windowToken, 0)
    it.clearFocus()
}