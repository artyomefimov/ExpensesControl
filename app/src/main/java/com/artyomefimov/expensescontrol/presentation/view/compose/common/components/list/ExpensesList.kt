package com.artyomefimov.expensescontrol.presentation.view.compose.common.components.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import androidx.compose.foundation.lazy.items

/**
 * Список трат, отображающий информацию из списка [ExpenseInfo]
 *
 * @param expenses список трат для отображения
 */
@Composable
fun ExpensesList(
    modifier: Modifier = Modifier,
    expenses: List<ExpenseInfo>,
) {
    LazyColumn(modifier = modifier) {
        items(items = expenses) { expense ->
            ExpenseInfoItem(expense = expense)
        }
    }
}
