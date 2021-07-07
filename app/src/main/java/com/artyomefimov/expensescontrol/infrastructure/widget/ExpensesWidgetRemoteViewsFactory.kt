package com.artyomefimov.expensescontrol.infrastructure.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.interactor.expense.ExpenseInteractor
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.mapper.mapList
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ExpensesWidgetRemoteViewsFactory(
    private val appContext: Context,
) : RemoteViewsService.RemoteViewsFactory {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface ExpensesWidgetRemoteViewsFactoryEntryPoint {

        fun interactor(): ExpenseInteractor

        fun expenseInfoMapper(): Mapper<Expense, ExpenseInfo>
    }

    private val expenseInteractor: ExpenseInteractor
    private val expenseInfoMapper: Mapper<Expense, ExpenseInfo>

    private var expenses: List<ExpenseInfo> = emptyList()

    init {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            ExpensesWidgetRemoteViewsFactoryEntryPoint::class.java
        )
        expenseInteractor = hiltEntryPoint.interactor()
        expenseInfoMapper = hiltEntryPoint.expenseInfoMapper()
    }

    override fun onDataSetChanged() {
        // works in binder thread pool
        runBlocking {
            expenses = expenseInteractor.getExpensesForCurrentMonth()
                .first()
                .mapList(expenseInfoMapper)
        }
    }

    override fun getViewAt(position: Int): RemoteViews {
        val expense = expenses[position]
        return RemoteViews(appContext.packageName, R.layout.widget_list_item).apply {
            setTextViewText(R.id.widgetSumTextView, expense.sum)
            setTextViewText(R.id.widgetCommentTextView, expense.comment)
            setTextViewText(R.id.widgetCategoryTextView, expense.category)
            setTextViewText(R.id.widgetTimestampTextView, expense.timestamp)
        }
    }

    override fun getCount(): Int = expenses.size

    override fun getItemId(position: Int): Long = expenses[position].id

    override fun getViewTypeCount(): Int = 1

    override fun onCreate() = Unit

    override fun onDestroy() = Unit

    override fun getLoadingView(): RemoteViews? = null

    override fun hasStableIds(): Boolean = true
}
