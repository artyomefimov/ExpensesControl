package com.artyomefimov.expensescontrol.presentation.viewmodel.statistics

import androidx.lifecycle.ViewModel
import com.artyomefimov.expensescontrol.presentation.model.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(

): ViewModel() {

    fun setPeriodFilter(dateEpochFrom: Long, dateEpochTo: Long) {

    }

    fun setCategoryFilter(category: String) {

    }

    fun setFilter(type: FilterType) {

    }
}