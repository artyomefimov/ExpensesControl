package com.artyomefimov.expensescontrol.presentation.viewmodel.expenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.interactor.expense.ExpenseInteractor
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractor
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.mapper.mapList
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.model.income.isZeroAndShouldBeEntered
import com.artyomefimov.expensescontrol.presentation.ext.formatToAmount
import com.artyomefimov.expensescontrol.presentation.ext.integerFormatter
import com.artyomefimov.expensescontrol.presentation.ext.toggle
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
    private val expenseInteractor: ExpenseInteractor,
    private val resourcesProvider: ResourcesProvider,
    private val expenseInfoMapper: Mapper<Expense, ExpenseInfo>,
): ViewModel() {

    private val navigateToEnterIncomeScreen = MutableLiveData<Event<Unit>>()
    private val showSnackbar = MutableLiveData<Event<Unit>>()
    private val availableDailySumState = MutableLiveData<AvailableSumInfo>()
    private val currentMonthExpensesState = MutableLiveData<List<ExpenseInfo>>()

    init {
        initialCheck()
        collectExpenses()
    }

    fun navigateToEnterIncomeScreen(): LiveData<Event<Unit>> = navigateToEnterIncomeScreen
    fun showSnackbar(): LiveData<Event<Unit>> = showSnackbar
    fun availableDailySumState(): LiveData<AvailableSumInfo> = availableDailySumState
    fun currentMonthExpensesState(): LiveData<List<ExpenseInfo>> = currentMonthExpensesState

    fun addExpense(
        stringSum: String?,
        comment: String?,
        category: String?,
    ) = viewModelScope.launch {
        if (stringSum.isNullOrEmpty() || category.isNullOrEmpty()) {
            showSnackbar.toggle()
            return@launch
        }

        expenseInteractor.addExpense(
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
        val dailySum = expenseInteractor
            .getAvailableDailySum()
            .let { integerFormatter.format(it) }
        val monthlySum = incomeInteractor
            .getIncomeForCurrentMonth()
            .value
            .let { integerFormatter.format(it) }
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
        expenseInteractor.getExpensesForCurrentMonth().collect {
            currentMonthExpensesState.value = it.mapList(expenseInfoMapper)
        }
    }
}
