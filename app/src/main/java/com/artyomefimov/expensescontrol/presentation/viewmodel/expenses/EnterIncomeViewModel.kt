package com.artyomefimov.expensescontrol.presentation.viewmodel.expenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractor
import com.artyomefimov.expensescontrol.presentation.ext.toggle
import com.artyomefimov.expensescontrol.presentation.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterIncomeViewModel @Inject constructor(
    private val incomeInteractor: IncomeInteractor,
): ViewModel() {

    private val navigateToExpenseScreen = MutableLiveData<Event<Unit>>()

    fun navigateToExpenseScreen(): LiveData<Event<Unit>> = navigateToExpenseScreen

    fun addIncome(stringSum: String?) = viewModelScope.launch {
        if (stringSum.isNullOrEmpty()) {
            return@launch
        }
        incomeInteractor.saveIncomeForNextMonth(stringSum)
        navigateToExpenseScreen.toggle()
    }
}
