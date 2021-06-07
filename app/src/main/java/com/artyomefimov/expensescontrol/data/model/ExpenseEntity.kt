package com.artyomefimov.expensescontrol.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import java.math.BigDecimal

const val TABLE_NAME = "expenses"
const val COLUMN_SUM = "sum"
const val COLUMN_COMMENT = "comment"
const val COLUMN_CATEGORY = "category"
const val COLUMN_TIMESTAMP = "timestamp"

@Entity(tableName = TABLE_NAME)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = COLUMN_SUM) val sum: BigDecimal,
    @ColumnInfo(name = COLUMN_COMMENT) val comment: String,
    @ColumnInfo(name = COLUMN_CATEGORY) val category: String,
    @ColumnInfo(name = COLUMN_TIMESTAMP) val timestamp: Instant,
)
