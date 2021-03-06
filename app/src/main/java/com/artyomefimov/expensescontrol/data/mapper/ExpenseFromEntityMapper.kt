package com.artyomefimov.expensescontrol.data.mapper

import com.artyomefimov.expensescontrol.data.model.ExpenseEntity
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import javax.inject.Inject

class ExpenseFromEntityMapper @Inject constructor(
) : Mapper<ExpenseEntity, Expense> {

    override fun map(input: ExpenseEntity): Expense {
        return Expense(
            id = input.id,
            sum = input.sum,
            comment = input.comment,
            category = input.category,
            timestamp = input.timestamp,
        )
    }
}
