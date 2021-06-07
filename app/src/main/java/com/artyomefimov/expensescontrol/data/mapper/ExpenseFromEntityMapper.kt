package com.artyomefimov.expensescontrol.data.mapper

import com.artyomefimov.expensescontrol.data.model.ExpenseEntity
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.model.Expense
import javax.inject.Inject

class ExpenseFromEntityMapper @Inject constructor(
) : Mapper<ExpenseEntity, Expense> {

    override fun map(input: ExpenseEntity): Expense {
        return Expense(
            sum = input.sum,
            comment = input.comment,
            category = input.category,
            timestamp = input.timestamp,
        )
    }
}