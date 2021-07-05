package com.artyomefimov.expensescontrol.presentation.view.statistics.dialogs

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.showPeriodSelectDialog(
    onPeriodSelected: (Long, Long) -> Unit,
    onCancel: () -> Unit,
) {
    val constraints = CalendarConstraints.Builder().build()
    val picker = MaterialDatePicker.Builder
        .dateRangePicker()
        .setCalendarConstraints(constraints)
        .build()
    picker.show(childFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        onPeriodSelected(it.first, it.second)
    }
    picker.addOnNegativeButtonClickListener { onCancel() }
}

fun Context.showCategoryDialog(
    items: Array<String>,
    onCategorySelected: (String) -> Unit,
    onCancel: () -> Unit,
) = MaterialAlertDialogBuilder(this)
    .setSingleChoiceItems(items, -1) { dialog, which ->
        onCategorySelected(items[which])
        dialog.dismiss()
    }
    .setOnCancelListener { onCancel() }
    .create()
    .show()
