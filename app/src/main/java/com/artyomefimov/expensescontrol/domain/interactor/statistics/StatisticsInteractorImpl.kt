package com.artyomefimov.expensescontrol.domain.interactor.statistics

import com.artyomefimov.expensescontrol.domain.ext.daysRange
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.model.statistics.FilteredExpensesResult
import com.artyomefimov.expensescontrol.domain.model.statistics.StatisticsFilter
import com.artyomefimov.expensescontrol.domain.repo.expense.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class StatisticsInteractorImpl @Inject constructor(
    private val repository: ExpenseRepository,
) : StatisticsInteractor {

    private val expensesFlow = MutableStateFlow(FilteredExpensesResult())

    override suspend fun applyFilter(filter: StatisticsFilter) {
        val periodRange = filter.periodFilter?.let { daysRange(it.from, it.to) }
        repository.allExpenses().collectLatest { expenses ->
            val resultList = expenses.toMutableList()
            periodRange?.let { range ->
                val result = resultList.filter { expense -> expense.timestamp in range }
                resultList.clear()
                resultList.addAll(result)
            }
            filter.categoryFilter?.let { category ->
                val result = resultList.filter { expense -> expense.category == category }
                resultList.clear()
                resultList.addAll(result)
            }
            if (filter.isMaxSumFilterEnabled) {
                resultList.maxByOrNull { it.sum }?.let { expenseWithMaxSum ->
                    resultList.clear()
                    resultList.add(expenseWithMaxSum)
                }
            }
            val commonSum = if (filter.isMaxSumFilterEnabled) null else calculateCommonSum(resultList)
            expensesFlow.value = expensesFlow.value.copy(
                expenses = resultList,
                commonSum = commonSum
            )
        }
    }

    override fun getFilteringResult(): StateFlow<FilteredExpensesResult> = expensesFlow

    private fun calculateCommonSum(expenses: List<Expense>) = expenses.sumOf { it.sum }
}