package com.artyomefimov.expensescontrol.data.mapper

import com.artyomefimov.expensescontrol.data.model.ExpenseEntity
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.model.Expense
import javax.inject.Inject

class ExpenseToEntityMapper @Inject constructor(
): Mapper<Expense, ExpenseEntity> {

    override fun map(input: Expense): ExpenseEntity {
        return ExpenseEntity(
            sum = input.sum,
            comment = input.comment,
            category = input.category,
            timestamp = input.timestamp,
        )
    }
}
