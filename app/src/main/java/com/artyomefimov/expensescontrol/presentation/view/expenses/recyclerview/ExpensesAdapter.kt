package com.artyomefimov.expensescontrol.presentation.view.expenses.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.model.Expense
import com.google.android.material.textview.MaterialTextView

class ExpensesAdapter(
    var items: List<Expense> = listOf()
): RecyclerView.Adapter<ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size
}

class ExpenseViewHolder(
    private val view: View,
): RecyclerView.ViewHolder(view) {
    fun bind(expense: Expense) {
        with(view) {
            // todo separate model, separate view, diffutil
            findViewById<MaterialTextView>(R.id.categoryTextView).text = expense.category
            findViewById<MaterialTextView>(R.id.commentTextView).text = expense.comment
            findViewById<MaterialTextView>(R.id.sumTextView).text = view.resources.getString(R.string.money_placeholder)
                .format(expense.sum.toString())
            findViewById<MaterialTextView>(R.id.timestampTextView).text = expense.timestamp.toString()
        }
    }
}