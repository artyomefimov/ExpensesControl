package com.artyomefimov.expensescontrol.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractor
import com.artyomefimov.expensescontrol.presentation.ext.toggle
import com.artyomefimov.expensescontrol.presentation.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnterIncomeViewModel @Inject constructor(
    private val incomeInteractor: IncomeInteractor,
): ViewModel() {

    private val navigateToExpenseScreen = MutableLiveData<Event<Unit>>()

    fun navigateToExpenseScreen(): LiveData<Event<Unit>> = navigateToExpenseScreen

    fun addIncome(stringSum: String?) {
        if (stringSum.isNullOrEmpty()) {
            return
        }
        incomeInteractor.saveIncomeForNextMonth(stringSum)
        navigateToExpenseScreen.toggle()
    }
}
