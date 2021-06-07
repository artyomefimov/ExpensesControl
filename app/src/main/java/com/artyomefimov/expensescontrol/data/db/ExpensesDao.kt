package com.artyomefimov.expensescontrol.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.artyomefimov.expensescontrol.data.model.ExpenseEntity
import com.artyomefimov.expensescontrol.data.model.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun allExpenses(): Flow<List<ExpenseEntity>>

    @Insert
    fun addExpense(entity: ExpenseEntity)
}