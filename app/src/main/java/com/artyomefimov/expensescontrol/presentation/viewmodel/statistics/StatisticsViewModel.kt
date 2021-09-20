package com.artyomefimov.expensescontrol.presentation.viewmodel.statistics

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.ext.toInstant
import com.artyomefimov.expensescontrol.domain.interactor.statistics.ChartInteractor
import com.artyomefimov.expensescontrol.domain.interactor.statistics.StatisticsInteractor
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.mapper.mapList
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.model.statistics.FilteredExpensesResult
import com.artyomefimov.expensescontrol.domain.model.statistics.PeriodFilter
import com.artyomefimov.expensescontrol.domain.model.statistics.StatisticsFilter
import com.artyomefimov.expensescontrol.domain.model.statistics.chart.ChartData
import com.artyomefimov.expensescontrol.presentation.ext.fractionFormatter
import com.artyomefimov.expensescontrol.presentation.model.Event
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import com.artyomefimov.expensescontrol.presentation.model.statistics.ChartDataUi
import com.artyomefimov.expensescontrol.presentation.model.statistics.FilterType
import com.artyomefimov.expensescontrol.presentation.model.statistics.StatisticsChipData
import com.artyomefimov.expensescontrol.presentation.model.statistics.StatisticsEvent
import com.artyomefimov.expensescontrol.presentation.resources.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val statisticsInteractor: StatisticsInteractor,
    private val chartInteractor: ChartInteractor,
    private val expenseInfoMapper: Mapper<Expense, ExpenseInfo>,
    private val chartDataMapper: Mapper<ChartData, ChartDataUi>,
) : ViewModel() {

    val commonSumState = mutableStateOf<String?>(null)
    val chartAvailabilityState = mutableStateOf(false)
    val statisticsViewEvent = mutableStateOf<Event<StatisticsEvent>>(Event.initial())
    val suitableExpensesState = mutableStateListOf<ExpenseInfo>()
    val selectedFiltersState = mutableStateListOf<StatisticsChipData>()
    val dialogState = mutableStateOf(false)

    private val availableCategories = resourcesProvider.getStringArray(R.array.categories_array)
    private var currentFilter = StatisticsFilter()
    private var currentExpenses = emptyList<Expense>()
    private val periodFilterData = StatisticsChipData(
        title = resourcesProvider.getString(R.string.chip_period),
        type = FilterType.PERIOD,
    )
    private val categoryFilterData = StatisticsChipData(
        title = resourcesProvider.getString(R.string.chip_category),
        type = FilterType.CATEGORY
    )
    private val maxSumFilterData = StatisticsChipData(
        title = resourcesProvider.getString(R.string.chip_max_sum),
        type = FilterType.MAX_SUM
    )

    val filters = listOf(periodFilterData, maxSumFilterData, categoryFilterData)

    var chartData: ChartDataUi? = null

    init {
        applyFilter(currentFilter)
        viewModelScope.launch {
            statisticsInteractor.getFilteringResult().collect(::processFilteringResult)
        }
        viewModelScope.launch {
            chartInteractor.getChartData().collect(::processChartData)
        }
    }

    fun hideChart() {
        dialogState.value = false
    }

    fun prepareDataForChart() = viewModelScope.launch {
        chartInteractor.prepareDataForChart(currentExpenses)
    }

    fun setPeriodFilter(dateEpochFrom: Long, dateEpochTo: Long) {
        val periodFilter = PeriodFilter(dateEpochFrom.toInstant(), dateEpochTo.toInstant())
        currentFilter = currentFilter.copy(periodFilter = periodFilter)
        selectedFiltersState.add(periodFilterData)
        applyFilter(currentFilter)
    }

    fun setCategoryFilter(category: String) {
        currentFilter = currentFilter.copy(categoryFilter = category)
        selectedFiltersState.add(categoryFilterData)
        applyFilter(currentFilter)
    }

    fun setFilter(data: StatisticsChipData) {
        // todo сделать единственное место-источник инфы про выделенные чипы
        when (data.type) {
            FilterType.PERIOD -> {
                if (currentFilter.periodFilter != null) {
                    currentFilter = currentFilter.copy(periodFilter = null)
                    selectedFiltersState.remove(data)
                    applyFilter(currentFilter)
                } else {
                    statisticsViewEvent.value = Event.create(StatisticsEvent.OpenPeriodDialog)
                }
            }
            FilterType.CATEGORY -> {
                if (currentFilter.categoryFilter != null) {
                    currentFilter = currentFilter.copy(categoryFilter = null)
                    selectedFiltersState.remove(data)
                    applyFilter(currentFilter)
                } else {
                    statisticsViewEvent.value = Event.create(
                        StatisticsEvent.OpenCategoryDialog(availableCategories)
                    )
                }
            }
            FilterType.MAX_SUM -> {
                val maxSumFilterValue = currentFilter.isMaxSumFilterEnabled.not()
                currentFilter = currentFilter.copy(isMaxSumFilterEnabled = maxSumFilterValue)
                if (maxSumFilterValue) {
                    selectedFiltersState.add(maxSumFilterData)
                } else {
                    selectedFiltersState.remove(maxSumFilterData)
                }
                applyFilter(currentFilter)
            }
        }
    }

    private fun applyFilter(filter: StatisticsFilter) = viewModelScope.launch {
        statisticsInteractor.applyFilter(filter)
    }

    private fun processFilteringResult(result: FilteredExpensesResult) {
        currentExpenses = result.expenses
        suitableExpensesState.apply {
            clear()
            addAll(result.expenses.mapList(expenseInfoMapper))
        }
        commonSumState.value = result.commonSum?.let { sum ->
            resourcesProvider.getString(R.string.common_sum)
                .format(fractionFormatter.format(sum))
        }
        chartAvailabilityState.value = result.isChartAvailable
    }

    private fun processChartData(chartData: ChartData) {
        if (chartData.data.isNotEmpty()) {
            this.chartData = chartDataMapper.map(chartData)
            statisticsViewEvent.value = Event.create(StatisticsEvent.OpenChartScreen)
            dialogState.value = true
        }
    }
}
