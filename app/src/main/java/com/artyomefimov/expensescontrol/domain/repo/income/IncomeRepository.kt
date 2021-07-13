package com.artyomefimov.expensescontrol.domain.repo.income

import kotlinx.datetime.Instant
import java.math.BigDecimal

/**
 * Репозиторий, который управляет доступной на месяц суммой
 */
interface IncomeRepository {

    /**
     * Обновить доступную сумму на месяц
     *
     * @param income новая доступная сумма на месяц
     */
    suspend fun updateIncome(income: BigDecimal)

    /**
     * Получить текущее значение доступной суммы на месяц
     */
    suspend fun getIncomeValue(): BigDecimal

    /**
     * Получить дату последнего изменения дохода в начале месяца
     */
    suspend fun getLastChangeDateString(): String

    /**
     * Установить дату последнего изменения дохода в начале месяца
     *
     * @param date новая дата
     */
    suspend fun setLastChangeDate(date: Instant)
}
