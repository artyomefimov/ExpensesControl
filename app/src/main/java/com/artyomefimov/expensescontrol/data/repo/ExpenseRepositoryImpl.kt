package com.artyomefimov.expensescontrol.data.repo

import com.artyomefimov.expensescontrol.data.db.ExpensesDb
import com.artyomefimov.expensescontrol.data.model.ExpenseEntity
import com.artyomefimov.expensescontrol.di.IoDispatcher
import com.artyomefimov.expensescontrol.domain.repo.ExpenseRepository
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.mapper.mapList
import com.artyomefimov.expensescontrol.domain.model.Expense
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val db: ExpensesDb,
    private val fromEntityMapper: Mapper<ExpenseEntity, Expense>,
    private val toEntityMapper: Mapper<Expense, ExpenseEntity>,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
): ExpenseRepository {

    override fun allExpenses(): Flow<List<Expense>> {
        return db.expensesDao().allExpenses().map {
            it.mapList(fromEntityMapper)
        }
    }

    override fun getExpensesForCurrentMonth(): Flow<List<Expense>> {
        return db.expensesDao().getExpensesForCurrentMonth().map {
            it.mapList(fromEntityMapper)
        }
    }

    override fun getExpensesForCurrentDay(): Flow<List<Expense>> {
        return db.expensesDao().getExpensesForCurrentDay().map {
            it.mapList(fromEntityMapper)
        }
    }

    override suspend fun addExpense(expense: Expense) {
        withContext(dispatcher) {
            db.expensesDao().addExpense(
                toEntityMapper.map(expense)
            )
        }
    }
}
