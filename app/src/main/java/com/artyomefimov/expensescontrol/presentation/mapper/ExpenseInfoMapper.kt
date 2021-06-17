package com.artyomefimov.expensescontrol.presentation.mapper

import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.model.Expense
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import com.artyomefimov.expensescontrol.presentation.resources.ResourcesProvider
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.math.BigDecimal
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import javax.inject.Inject

class ExpenseInfoMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
) : Mapper<Expense, ExpenseInfo> {

    private val formatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    override fun map(input: Expense): ExpenseInfo {
        return ExpenseInfo(
            id = input.id,
            sum = input.sum.formatSum(),
            comment = input.comment,
            category = input.category,
            timestamp = input.timestamp.formatToString(),
        )
    }

    private fun BigDecimal.formatSum(): String {
        return resourcesProvider.getString(R.string.money_placeholder)
            .format(this.toString())
    }

    private fun Instant.formatToString(): String {
        return formatter.format(this.toJavaInstant()).orEmpty()
    }
}
