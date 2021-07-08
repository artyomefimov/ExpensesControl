package com.artyomefimov.expensescontrol.presentation.viewmodel.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.ext.toInstant
import com.artyomefimov.expensescontrol.domain.interactor.statistics.StatisticsInteractor
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.mapper.mapList
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.model.statistics.PeriodFilter
import com.artyomefimov.expensescontrol.domain.model.statistics.StatisticsFilter
import com.artyomefimov.expensescontrol.presentation.ext.fractionFormatter
import com.artyomefimov.expensescontrol.presentation.ext.toggle
import com.artyomefimov.expensescontrol.presentation.model.Event
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import com.artyomefimov.expensescontrol.presentation.model.FilterType
import com.artyomefimov.expensescontrol.presentation.resources.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    resourcesProvider: ResourcesProvider,
    private val interactor: StatisticsInteractor,
    private val expenseInfoMapper: Mapper<Expense, ExpenseInfo>,
) : ViewModel() {

    private val currentFiltersState = MutableLiveData<StatisticsFilter>()
    private val selectedCategoryState = MutableLiveData<String>()
    private val suitableExpensesState = MutableLiveData<List<ExpenseInfo>>()
    private val commonSumState = MutableLiveData<String?>()
    private val chartAvailabilityState = MutableLiveData<Boolean>()
    private val showPeriodDialogViewEvent = MutableLiveData<Event<Unit>>()
    private val showCategoryDialogViewEvent = MutableLiveData<Event<Array<String>>>()

    private val availableCategories = resourcesProvider.getStringArray(R.array.categories_array)
    private val defaultCategoryText = resourcesProvider.getString(R.string.chip_category)
    private var currentFilter = StatisticsFilter()

    init {
        applyFilterToInteractor(currentFilter)
        viewModelScope.launch {
            interactor.getFilteringResult().collect { result ->
                suitableExpensesState.value = result.expenses.mapList(expenseInfoMapper)
                commonSumState.value = result.commonSum?.let { sum ->
                    resourcesProvider.getString(R.string.common_sum)
                        .format(fractionFormatter.format(sum))
                }
                chartAvailabilityState.value = result.isChartAvailable
            }
        }
    }

    fun currentFiltersState(): LiveData<StatisticsFilter> = currentFiltersState
    fun selectedCategoryState(): LiveData<String> = selectedCategoryState
    fun suitableExpensesState(): LiveData<List<ExpenseInfo>> = suitableExpensesState
    fun commonSumState(): LiveData<String?> = commonSumState
    fun chartAvailabilityState(): LiveData<Boolean> = chartAvailabilityState
    fun showPeriodDialogViewEvent(): LiveData<Event<Unit>> = showPeriodDialogViewEvent
    fun showCategoryDialogViewEvent(): LiveData<Event<Array<String>>> = showCategoryDialogViewEvent

    fun setPeriodFilter(dateEpochFrom: Long, dateEpochTo: Long) {
        val periodFilter = PeriodFilter(dateEpochFrom.toInstant(), dateEpochTo.toInstant())
        currentFilter = currentFilter.copy(periodFilter = periodFilter)
        applyFilterToInteractor(currentFilter)
    }

    fun setCategoryFilter(category: String) {
        currentFilter = currentFilter.copy(categoryFilter = category)
        applyFilterToInteractor(currentFilter)
        selectedCategoryState.value = category
    }

    fun setFilter(type: FilterType) {
        when (type) {
            FilterType.PERIOD -> {
                if (currentFilter.periodFilter != null) {
                    currentFilter = currentFilter.copy(periodFilter = null)
                    applyFilterToInteractor(currentFilter)
                } else {
                    showPeriodDialogViewEvent.toggle()
                }
            }
            FilterType.CATEGORY -> {
                if (currentFilter.categoryFilter != null) {
                    currentFilter = currentFilter.copy(categoryFilter = null)
                    applyFilterToInteractor(currentFilter)
                    selectedCategoryState.value = defaultCategoryText
                } else {
                    showCategoryDialogViewEvent.value = Event(availableCategories)
                }
            }
            FilterType.MAX_SUM -> {
                val maxSumFilterValue = currentFilter.isMaxSumFilterEnabled.not()
                currentFilter = currentFilter.copy(isMaxSumFilterEnabled = maxSumFilterValue)
                applyFilterToInteractor(currentFilter)
            }
        }
    }

    private fun applyFilterToInteractor(filter: StatisticsFilter) = viewModelScope.launch {
        interactor.applyFilter(filter)
    }
}
