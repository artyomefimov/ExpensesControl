package com.artyomefimov.expensescontrol.domain.interactor.category

/**
 * Работает с категориями трат, доступными пользователю
 */
interface CategoryInteractor {

    /**
     * Получает список категорий трат, доступных пользователю
     *
     * @return [List] список категорий трат
     */
    fun getCategories(): List<String>
}
