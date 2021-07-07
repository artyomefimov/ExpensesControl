package com.artyomefimov.expensescontrol.infrastructure.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.ext.appWidgetManager
import com.artyomefimov.expensescontrol.domain.ext.getAppWidgetIds
import com.artyomefimov.expensescontrol.domain.interactor.expense.ExpenseInteractor
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Implementation of App Widget functionality.
 */
@AndroidEntryPoint
class ExpensesControlAppWidget : AppWidgetProvider() {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context
    @Inject
    lateinit var expenseInteractor: ExpenseInteractor

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    init {
        scope.launch {
            // https://github.com/google/dagger/issues/2741
//            if (::expenseInteractor.isInitialized && ::appContext.isInitialized) {
                expenseInteractor.getExpensesForCurrentDay().collectLatest {
                    appContext.appWidgetManager.notifyAppWidgetViewDataChanged(
                        appContext.getAppWidgetIds(),
                        R.id.widgetExpensesListView
                    )
                }
            }
//        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.expenses_control_app_widget).apply {
            setOnClickPendingIntent(R.id.widgetAddImageView, getDeepLinkToExpensesScreen(context))
            setRemoteAdapter(R.id.widgetExpensesListView, getIntentToRemoteViewsService(context))
        }
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun getDeepLinkToExpensesScreen(context: Context): PendingIntent {
        return NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_navigation)
            .setDestination(R.id.expenses_dest)
            .createPendingIntent()
    }

    private fun getIntentToRemoteViewsService(context: Context): Intent {
        return Intent(context, ExpensesWidgetRemoteViewsService::class.java)
    }
}
