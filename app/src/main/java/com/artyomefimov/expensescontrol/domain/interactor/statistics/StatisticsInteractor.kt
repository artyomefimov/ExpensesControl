package com.artyomefimov.expensescontrol.domain.interactor.statistics

import com.artyomefimov.expensescontrol.domain.model.statistics.FilteredExpensesResult
import com.artyomefimov.expensescontrol.domain.model.statistics.StatisticsFilter
import kotlinx.coroutines.flow.StateFlow

/**
 * Содержит бизнес-логику для сбора статистики по текущим расходам
 */
interface StatisticsInteractor {

    /**
     * Фильтрует текущие расходы согласно фильтру [StatisticsFilter]
     *
     * @param filter применяемый фильтр
     */
    suspend fun applyFilter(filter: StatisticsFilter)

    /**
     * Получает результат фильтрации в виде [StateFlow]
     */
    fun getFilteringResult(): StateFlow<FilteredExpensesResult>
}
