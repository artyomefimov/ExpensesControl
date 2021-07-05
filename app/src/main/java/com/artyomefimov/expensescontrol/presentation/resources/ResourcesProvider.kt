package com.artyomefimov.expensescontrol.presentation.resources

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

/**
 * Провайдер ресурсов, инкапсулирующий контекст приложения, чтобы не ссылаться на него напрямую
 */
interface ResourcesProvider {

    /**
     * Получить строку по id ресурса
     *
     * @param resId id ресурса
     */
    fun getString(@StringRes resId: Int): String

    /**
     * Получить массив строк по id ресурса
     *
     * @param resId id ресурса
     */
    fun getStringArray(@ArrayRes resId: Int): Array<String>
}
