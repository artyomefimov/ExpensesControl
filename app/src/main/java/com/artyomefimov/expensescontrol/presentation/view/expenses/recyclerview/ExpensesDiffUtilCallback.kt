package com.artyomefimov.expensescontrol.presentation.view.expenses.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo

class ExpensesDiffUtilCallback(
    private val oldItems: List<ExpenseInfo>,
    private val newItems: List<ExpenseInfo>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean = oldItems[oldItemPosition].id == newItems[newItemPosition].id

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean = oldItems[oldItemPosition] == newItems[newItemPosition]
}
