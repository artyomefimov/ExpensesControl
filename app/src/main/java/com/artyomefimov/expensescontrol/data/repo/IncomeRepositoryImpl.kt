package com.artyomefimov.expensescontrol.data.repo

import android.content.SharedPreferences
import androidx.core.content.edit
import com.artyomefimov.expensescontrol.domain.repo.IncomeRepository
import kotlinx.datetime.Instant
import java.math.BigDecimal
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val preferences: SharedPreferences,
): IncomeRepository {

    private companion object {
        const val CURRENT_MONTH_INCOME_KEY = "CURRENT_MONTH_INCOME_KEY"
        const val LAST_CHANGE_DATE_KEY = "LAST_CHANGE_DATE_KEY"
    }

    override fun updateIncome(income: BigDecimal) {
        preferences.edit {
            putString(CURRENT_MONTH_INCOME_KEY, income.toString())
        }
    }

    override fun getIncomeValue(): BigDecimal {
        val stringValue = preferences.getString(
            CURRENT_MONTH_INCOME_KEY,
            BigDecimal.ZERO.toString()
        )
        return BigDecimal(stringValue)
    }

    override fun getLastChangeDateString(): String {
        return preferences.getString(LAST_CHANGE_DATE_KEY, "").orEmpty()
    }

    override fun setLastChangeDate(date: Instant) {
        preferences.edit {
            putString(LAST_CHANGE_DATE_KEY, date.toString())
        }
    }
}
