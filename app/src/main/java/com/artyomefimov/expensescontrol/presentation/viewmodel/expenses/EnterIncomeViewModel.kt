package com.artyomefimov.expensescontrol.presentation.viewmodel.expenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractor
import com.artyomefimov.expensescontrol.presentation.ext.isLessThanMinimalSum
import com.artyomefimov.expensescontrol.presentation.ext.toggle
import com.artyomefimov.expensescontrol.presentation.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class EnterIncomeViewModel @Inject constructor(
    private val incomeInteractor: IncomeInteractor,
): ViewModel() {

    private val navigateToExpenseScreen = MutableLiveData<Event<Unit>>()
    private val incorrectSum = MutableLiveData<Event<Unit>>()

    fun navigateToExpenseScreen(): LiveData<Event<Unit>> = navigateToExpenseScreen
    fun incorrectSum(): LiveData<Event<Unit>> = incorrectSum

    fun addIncome(stringSum: String?) = viewModelScope.launch {
        if (stringSum.isNullOrEmpty()) {
            return@launch
        }
        if (stringSum.isLessThanMinimalSum()) {
            incorrectSum.toggle()
        } else {
            incomeInteractor.saveIncomeForNextMonth(stringSum)
            navigateToExpenseScreen.toggle()
        }
    }
}
