package com.artyomefimov.expensescontrol.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.interactor.dailyexpense.DailyExpenseInteractor
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractor
import com.artyomefimov.expensescontrol.domain.model.isZeroAndShouldBeEntered
import com.artyomefimov.expensescontrol.presentation.ext.toggle
import com.artyomefimov.expensescontrol.presentation.model.AvailableSumInfo
import com.artyomefimov.expensescontrol.presentation.model.Event
import com.artyomefimov.expensescontrol.presentation.resources.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnterExpenseViewModel @Inject constructor(
    private val incomeInteractor: IncomeInteractor,
    private val dailyExpenseInteractor: DailyExpenseInteractor,
    private val resourcesProvider: ResourcesProvider,
): ViewModel() {

    private val navigateToEnterIncomeScreen = MutableLiveData<Event<Unit>>()
    private val availableDailySumState = MutableLiveData<AvailableSumInfo>()

    init {
        initialCheck()
    }

    fun navigateToEnterIncomeScreen(): LiveData<Event<Unit>> = navigateToEnterIncomeScreen
    fun availableDailySumState(): LiveData<AvailableSumInfo> = availableDailySumState

    fun addExpense(
        stringSum: String?,
        comment: String?,
    ) {
        if (stringSum.isNullOrEmpty()) {
            return
        }

        dailyExpenseInteractor.addExpense(
            stringSum = stringSum,
            comment = comment.orEmpty(),
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
        val sum = dailyExpenseInteractor
            .getAvailableDailySum()
            .toString()
        val availableSum = resourcesProvider.getString(R.string.available_money_placeholder)
            .format(sum)
        availableDailySumState.value = AvailableSumInfo(
            availableSum = availableSum,
            isInitial = isInitial,
        )
    }
}