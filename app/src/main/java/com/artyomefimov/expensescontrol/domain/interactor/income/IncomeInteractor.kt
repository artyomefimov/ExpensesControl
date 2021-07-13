package com.artyomefimov.expensescontrol.domain.interactor.income

import com.artyomefimov.expensescontrol.domain.model.income.Income

/**
 * Содержит бизнес-логику для суммы дохода на текущий месяц
 */
interface IncomeInteractor {

    /**
     * Получение дохода за текущий месяц
     *
     * @return [Income]
     */
    suspend fun getIncomeForCurrentMonth(): Income

    /**
     * Сохранение планируемой суммы дохода в начале месяца
     *
     * @param incomeString строковое представление планируемой суммы дохода
     * в начале месяца
     */
    suspend fun saveIncomeForNextMonth(incomeString: String)
}
