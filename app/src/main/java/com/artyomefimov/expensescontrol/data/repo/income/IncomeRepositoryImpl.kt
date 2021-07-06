package com.artyomefimov.expensescontrol.data.repo.income

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.artyomefimov.expensescontrol.domain.repo.income.IncomeRepository
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Instant
import java.math.BigDecimal
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : IncomeRepository {

    private companion object {
        val CURRENT_MONTH_INCOME_KEY = stringPreferencesKey(
            "CURRENT_MONTH_INCOME_KEY"
        )
        val LAST_CHANGE_DATE_KEY = stringPreferencesKey(
            "LAST_CHANGE_DATE_KEY"
        )
    }

    override suspend fun updateIncome(income: BigDecimal) {
        dataStore.edit { prefs ->
            prefs[CURRENT_MONTH_INCOME_KEY] = income.toString()
        }
    }

    override suspend fun getIncomeValue(): BigDecimal {
        val stringValue = dataStore.data.first()[CURRENT_MONTH_INCOME_KEY]
        return BigDecimal(stringValue)
    }

    override suspend fun getLastChangeDateString(): String {
        return dataStore.data.first()[LAST_CHANGE_DATE_KEY].orEmpty()
    }

    override suspend fun setLastChangeDate(date: Instant) {
        dataStore.edit { prefs ->
            prefs[LAST_CHANGE_DATE_KEY] = date.toString()
        }
    }
}
