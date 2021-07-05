package com.artyomefimov.expensescontrol.presentation.model

/**
 * Тип фильтра для трат
 */
enum class FilterType {
    /**
     * Фильтр по периоду, в течение которого была совершена трата
     */
    PERIOD,
    /**
     * Фильтр по категории траты
     */
    CATEGORY,
    /**
     * Фильтр по максимальной сумме траты
     */
    MAX_SUM
}
