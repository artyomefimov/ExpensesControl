package com.artyomefimov.expensescontrol.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.interactor.dailyexpense.DailyExpenseInteractor
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractor
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.mapper.mapList
import com.artyomefimov.expensescontrol.domain.model.Expense
import com.artyomefimov.expensescontrol.domain.model.isZeroAndShouldBeEntered
import com.artyomefimov.expensescontrol.presentation.ext.toggle
import com.artyomefimov.expensescontrol.presentation.mapper.ExpenseInfoMapper
import com.artyomefimov.expensescontrol.presentation.model.AvailableSumInfo
import com.artyomefimov.expensescontrol.presentation.model.Event
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import com.artyomefimov.expensescontrol.presentation.resources.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterExpenseViewModel @Inject constructor(
    private val incomeInteractor: IncomeInteractor,
    private val dailyExpenseInteractor: DailyExpenseInteractor,
    private val resourcesProvider: ResourcesProvider,
    private val expenseInfoMapper: Mapper<Expense, ExpenseInfo>,
): ViewModel() {

    private val navigateToEnterIncomeScreen = MutableLiveData<Event<Unit>>()
    private val availableDailySumState = MutableLiveData<AvailableSumInfo>()
    private val currentMonthExpensesState = MutableLiveData<List<ExpenseInfo>>()

    init {
        initialCheck()
        collectExpenses()
    }

    fun navigateToEnterIncomeScreen(): LiveData<Event<Unit>> = navigateToEnterIncomeScreen
    fun availableDailySumState(): LiveData<AvailableSumInfo> = availableDailySumState
    fun currentMonthExpensesState(): LiveData<List<ExpenseInfo>> = currentMonthExpensesState

    fun addExpense(
        stringSum: String?,
        comment: String?,
        category: String,
    ) = viewModelScope.launch {
        if (stringSum.isNullOrEmpty()) {
            return@launch
        }

        dailyExpenseInteractor.addExpense(
            stringSum = stringSum,
            comment = comment.orEmpty(),
            category = category,
        )
        updateAvailableSum(isInitial = false)
    }

    private fun initialCheck() {
        if (shouldEnterIncome()) {
            navigateToEnterIncomeScreen.toggle()
        } else {
            updateAvailableSum(isInitial = true)
        }
    }

    private fun shouldEnterIncome(): Boolean {
        return incomeInteractor.getIncomeForCurrentMonth()
            .isZeroAndShouldBeEntered()
    }

    private fun updateAvailableSum(isInitial: Boolean) {
        val dailySum = dailyExpenseInteractor
            .getAvailableDailySum()
            .toString()
        val monthlySum = incomeInteractor
            .getIncomeForCurrentMonth()
            .value
            .toString()
        val availableDailySum = resourcesProvider.getString(R.string.available_money_placeholder)
            .format(dailySum)
        val availableMonthlySum = resourcesProvider.getString(R.string.money_left_label_text)
            .format(monthlySum)
        availableDailySumState.value = AvailableSumInfo(
            availableDailySum = availableDailySum,
            availableMonthlySum = availableMonthlySum,
            isInitial = isInitial,
        )
    }

    private fun collectExpenses() = viewModelScope.launch {
        dailyExpenseInteractor.getAllExpensesForCurrentMonth().collect {
            currentMonthExpensesState.value = it.mapList(expenseInfoMapper)
        }
    }
}