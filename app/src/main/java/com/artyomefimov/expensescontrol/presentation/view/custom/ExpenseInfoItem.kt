package com.artyomefimov.expensescontrol.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.artyomefimov.expensescontrol.databinding.ItemExpenseInfoBinding
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo

class ExpenseInfoItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ItemExpenseInfoBinding = ItemExpenseInfoBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setExpense(expense: ExpenseInfo) = with(binding) {
        categoryTextView.text = expense.category
        commentTextView.text = expense.comment
        sumTextView.text = expense.sum
        timestampTextView.text = expense.timestamp
    }
}
