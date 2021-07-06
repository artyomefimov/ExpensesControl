package com.artyomefimov.expensescontrol.infrastructure.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder
import com.artyomefimov.expensescontrol.R

/**
 * Implementation of App Widget functionality.
 */
class ExpensesControlAppWidget : AppWidgetProvider() {

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
