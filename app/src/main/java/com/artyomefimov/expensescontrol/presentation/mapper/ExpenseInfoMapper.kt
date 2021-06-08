package com.artyomefimov.expensescontrol.presentation.mapper

import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.model.Expense
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import com.artyomefimov.expensescontrol.presentation.resources.ResourcesProvider
import javax.inject.Inject

class ExpenseInfoMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
) : Mapper<Expense, ExpenseInfo> {

    override fun map(input: Expense): ExpenseInfo {
        val sum = resourcesProvider.getString(R.string.money_placeholder)
            .format(input.sum.toString())
        return ExpenseInfo(
            id = input.id,
            sum = sum,
            comment = input.comment,
            category = input.category,
            timestamp = input.timestamp.toString(),
        )
    }
}