package com.artyomefimov.expensescontrol.infrastructure.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo

class ExpensesWidgetRemoteViewsFactory(
    private val appContext: Context,
    private val expenses: List<ExpenseInfo>,
) : RemoteViewsService.RemoteViewsFactory {

    override fun getViewAt(position: Int): RemoteViews {
        val expense = expenses[position]
        return RemoteViews(appContext.packageName, R.layout.widget_list_item).apply {
            setTextViewText(R.id.widgetCategoryTextView, expense.category)
            setTextViewText(R.id.widgetSumTextView, expense.sum)
        }
    }

    override fun getCount(): Int = expenses.size

    override fun getItemId(position: Int): Long = expenses[position].id

    override fun getViewTypeCount(): Int = 1

    override fun onCreate() = Unit

    override fun onDataSetChanged() = Unit

    override fun onDestroy() = Unit

    override fun getLoadingView(): RemoteViews? = null

    override fun hasStableIds(): Boolean = true
}