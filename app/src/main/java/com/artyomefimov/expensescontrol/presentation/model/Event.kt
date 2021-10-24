package com.artyomefimov.expensescontrol.presentation.model

/**
 * Событие с данными, доступными для обработки только один раз
 */
class Event<out T>(
    private val content: T?
) {

    /**
     * Флаг, показывающий были ли данные уже обработаны
     */
    private var isHandled = false

    /**
     * Получает данные эвента, если они еще не были обработаны, иначе null
     */
    fun getContent(): T? {
        return if (isHandled) {
            null
        } else {
            isHandled = true
            content
        }
    }

    companion object {
        fun initial() = Event(null)
        fun <T> create(content: T) = Event(content)
    }
}
