package com.artyomefimov.expensescontrol.infrastructure.widget

import android.content.Intent
import android.widget.RemoteViewsService

class ExpensesWidgetRemoteViewsService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return ExpensesWidgetRemoteViewsFactory(applicationContext)
    }
}
