package com.artyomefimov.expensescontrol.presentation.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import com.artyomefimov.expensescontrol.presentation.view.custom.ExpenseInfoItem

/**
 * Адаптер для списка трат
 */
class ExpensesAdapter(
    private val items: MutableList<ExpenseInfo> = mutableListOf()
): RecyclerView.Adapter<ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    fun swapData(newItems: List<ExpenseInfo>) {
        this.items.clear()
        this.items.addAll(newItems)
    }

    fun getItems(): List<ExpenseInfo> = items
}

class ExpenseViewHolder(
    private val view: View,
): RecyclerView.ViewHolder(view) {

    fun bind(expense: ExpenseInfo) = (view as ExpenseInfoItem).setExpense(expense)
}
