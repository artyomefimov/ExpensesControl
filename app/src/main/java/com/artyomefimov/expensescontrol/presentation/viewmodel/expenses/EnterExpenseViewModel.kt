package com.artyomefimov.expensescontrol.presentation.viewmodel.expenses

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.interactor.category.CategoryInteractor
import com.artyomefimov.expensescontrol.domain.interactor.date.DateInteractor
import com.artyomefimov.expensescontrol.domain.interactor.expense.ExpenseInteractor
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractor
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.mapper.mapList
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.presentation.ext.formatToAmount
import com.artyomefimov.expensescontrol.presentation.ext.integerFormatter
import com.artyomefimov.expensescontrol.presentation.ext.isLessThanMinimalSum
import com.artyomefimov.expensescontrol.presentation.model.ChipData
import com.artyomefimov.expensescontrol.presentation.model.Event
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import com.artyomefimov.expensescontrol.presentation.model.expense.AvailableSumInfo
import com.artyomefimov.expensescontrol.presentation.model.expense.ExpenseEvent
import com.artyomefimov.expensescontrol.presentation.model.expense.ExpensesChipData
import com.artyomefimov.expensescontrol.presentation.resources.ResourcesProvider
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.edittext.emptyTextFieldValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterExpenseViewModel @Inject constructor(
    categoryInteractor: CategoryInteractor,
    private val incomeInteractor: IncomeInteractor,
    private val expenseInteractor: ExpenseInteractor,
    private val dateInteractor: DateInteractor,
    private val resourcesProvider: ResourcesProvider,
    private val expenseInfoMapper: Mapper<Expense, ExpenseInfo>,
) : ViewModel() {

    val amountState = mutableStateOf(emptyTextFieldValue())
    val commentState = mutableStateOf("")
    val availableSumState = mutableStateOf(AvailableSumInfo())
    val expenseScreenEvent = mutableStateOf<Event<ExpenseEvent>>(Event.initial())
    val currentMonthExpensesState = mutableStateListOf<ExpenseInfo>()
    val selectedCategoryState = mutableStateOf<ChipData?>(null)

    val allCategories: List<ExpensesChipData>

    init {
        viewModelScope.launch {
            updateAvailableSum(isInitial = true)
        }
        allCategories = categoryInteractor.getCategories().map { ExpensesChipData(it) }
        collectExpenses()
    }

    fun updateSelectedCategory(data: ChipData) {
        selectedCategoryState.value = if (selectedCategoryState.value == data) {
            null
        } else {
            data
        }
    }

    fun updateAmount(amount: TextFieldValue) {
        this.amountState.value = amount
    }

    fun updateComment(comment: String) {
        this.commentState.value = comment
    }

    fun addExpense() {
        viewModelScope.launch {
            val sum = amountState.value.text.formatToAmount()
            val comment = commentState.value
            val category = selectedCategoryState.value?.title.orEmpty()
            when {
                sum.isNullOrEmpty() || category.isEmpty() -> {
                    expenseScreenEvent.value =
                        messageEvent(R.string.absent_expense_parameters_message)
                }
                sum.isLessThanMinimalSum() -> {
                    expenseScreenEvent.value = messageEvent(R.string.incorrect_sum)
                }
                else -> {
                    expenseInteractor.addExpense(
                        stringSum = sum,
                        comment = comment,
                        category = category,
                    )
                    updateAvailableSum(isInitial = false)
                    clearValues()
                }
            }
        }
    }

    private suspend fun updateAvailableSum(isInitial: Boolean) {
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
            .format(monthlySum, dateInteractor.getCurrentMonthNameAndLastDay())
        availableSumState.value = AvailableSumInfo(
            availableDailySum = availableDailySum,
            availableMonthlySum = availableMonthlySum,
            isInitial = isInitial,
        )
    }

    private fun collectExpenses() = viewModelScope.launch {
        expenseInteractor.getExpensesForCurrentMonth().collect {
            currentMonthExpensesState.apply {
                clear()
                addAll(it.mapList(expenseInfoMapper))
            }
        }
    }

    private fun clearValues() {
        selectedCategoryState.value = null
        amountState.value = emptyTextFieldValue()
        commentState.value = ""
    }

    private fun messageEvent(
        @StringRes resId: Int
    ): Event<ExpenseEvent> {
        return Event.create(ExpenseEvent.MessageEvent(resourcesProvider.getString(resId)))
    }
}
