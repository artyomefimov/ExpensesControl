package com.artyomefimov.expensescontrol.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.artyomefimov.expensescontrol.data.model.COLUMN_TIMESTAMP
import com.artyomefimov.expensescontrol.data.model.ExpenseEntity
import com.artyomefimov.expensescontrol.data.model.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun allExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_TIMESTAMP BETWEEN DATETIME('now','localtime','start of month') AND DATETIME('now','localtime', 'start of month','+1 month','-1 day')")
    fun getExpensesForCurrentMonth(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_TIMESTAMP BETWEEN DATETIME('now','localtime','start of day') AND DATETIME('now','localtime','start of day','+1 day')")
    fun getExpensesForCurrentDay(): Flow<List<ExpenseEntity>>

    @Insert
    suspend fun addExpense(entity: ExpenseEntity)
}