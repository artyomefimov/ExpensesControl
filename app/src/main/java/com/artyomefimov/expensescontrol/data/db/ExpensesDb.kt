package com.artyomefimov.expensescontrol.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artyomefimov.expensescontrol.data.converter.Converters
import com.artyomefimov.expensescontrol.data.model.ExpenseEntity

const val DB_VERSION = 1
const val DB_NAME = "ExpensesDb"

@Database(entities = [ExpenseEntity::class], version = DB_VERSION)
@TypeConverters(Converters::class)
abstract class ExpensesDb : RoomDatabase() {

    abstract fun expensesDao(): ExpensesDao
}