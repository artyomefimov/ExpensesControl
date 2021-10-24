package com.artyomefimov.expensescontrol.infrastructure.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkBuilder
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.ext.appWidgetManager
import com.artyomefimov.expensescontrol.domain.ext.getAppWidgetIds
import com.artyomefimov.expensescontrol.presentation.view.compose.common.navigation.Routes

/**
 * [AppWidgetProvider] для виджета расходов на лаунчере
 */
class ExpensesControlAppWidget : AppWidgetProvider() {

    private companion object {
        const val REQUEST_CODE = 123455
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        context.appWidgetManager.notifyAppWidgetViewDataChanged(
            context.getAppWidgetIds(),
            R.id.widgetExpensesListView
        )
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.widget_expenses_control_app).apply {
            setOnClickPendingIntent(R.id.widgetAddImageView, getDeepLinkToExpensesScreen(context))
            setRemoteAdapter(R.id.widgetExpensesListView, getIntentToRemoteViewsService(context))
        }
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun getDeepLinkToExpensesScreen(context: Context): PendingIntent {
        val intent = Intent(Intent.ACTION_VIEW, "https://example.com/open_expenses".toUri())
        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT) ?: error("No pending intent")
        }
    }

    private fun getIntentToRemoteViewsService(context: Context): Intent {
        return Intent(context, ExpensesWidgetRemoteViewsService::class.java)
    }
}
