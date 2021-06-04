package com.artyomefimov.expensescontrol.domain.interactor.income

import com.artyomefimov.expensescontrol.domain.model.Income
import java.math.BigDecimal

/**
 * Содержит бизнес-логику для суммы дохода на текущий месяц
 */
interface IncomeInteractor {

    /**
     * Получение дохода за текущий месяц
     *
     * @return [Income]
     */
    fun getIncomeForCurrentMonth(): Income

    /**
     * Сохранение планируемой суммы дохода в начале месяца
     *
     * @param incomeString строковое представление планируемой суммы дохода
     * в начале месяца
     */
    fun saveIncomeForNextMonth(incomeString: String)
}