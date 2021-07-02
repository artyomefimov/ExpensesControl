package com.artyomefimov.expensescontrol.di

import android.content.Context
import android.content.SharedPreferences
import androidx.work.Configuration
import androidx.work.WorkerFactory
import com.artyomefimov.expensescontrol.domain.interactor.expense.ExpenseInteractor
import com.artyomefimov.expensescontrol.infrastructure.ExpensesWorkerFactory
import com.artyomefimov.expensescontrol.infrastructure.NotificationBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvidesModule {

    private companion object {
        const val PREFS_FILE_NAME = "expenses_control"
    }

    @Provides
    @Singleton
    fun providePreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences(
        PREFS_FILE_NAME,
        Context.MODE_PRIVATE
    )

    @Provides
    @Singleton
    fun provideClock(): Clock = Clock.System

    @Provides
    @Singleton
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideWorkerFactory(
        expenseInteractor: ExpenseInteractor,
        notificationBuilder: NotificationBuilder,
    ): WorkerFactory = ExpensesWorkerFactory(
        expenseInteractor,
        notificationBuilder,
    )

    @Provides
    @Singleton
    fun provideWorkManagerConfiguration(
        workerFactory: WorkerFactory,
    ): Configuration = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()
}
