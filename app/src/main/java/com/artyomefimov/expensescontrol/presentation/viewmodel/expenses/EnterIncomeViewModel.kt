package com.artyomefimov.expensescontrol.presentation.viewmodel.expenses

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractor
import com.artyomefimov.expensescontrol.presentation.ext.formatToAmount
import com.artyomefimov.expensescontrol.presentation.ext.isLessThanMinimalSum
import com.artyomefimov.expensescontrol.presentation.model.Event
import com.artyomefimov.expensescontrol.presentation.model.income.IncomeEvent
import com.artyomefimov.expensescontrol.presentation.resources.ResourcesProvider
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.edittext.emptyTextFieldValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterIncomeViewModel @Inject constructor(
    private val incomeInteractor: IncomeInteractor,
    private val resourcesProvider: ResourcesProvider,
) : ViewModel() {

    private val amount = mutableStateOf(emptyTextFieldValue())
    private val incomeEvent = mutableStateOf<Event<IncomeEvent>>(Event.initial())

    fun incomeEvent(): State<Event<IncomeEvent>> = incomeEvent
    fun amountState(): State<TextFieldValue> = amount

    fun updateAmount(amount: TextFieldValue) {
        this.amount.value = amount
    }

    fun addIncome() {
        viewModelScope.launch {
            val stringSum = amount.value.text.formatToAmount()
            if (stringSum.isNullOrEmpty() || stringSum.isLessThanMinimalSum()) {
                incomeEvent.value = Event(
                    IncomeEvent.ShowIncorrectSumMessage(
                        message = resourcesProvider.getString(R.string.incorrect_sum)
                    )
                )
            } else {
                incomeInteractor.saveIncomeForNextMonth(stringSum)
                incomeEvent.value = Event(IncomeEvent.NavigateToExpense)
            }
        }
    }
}
