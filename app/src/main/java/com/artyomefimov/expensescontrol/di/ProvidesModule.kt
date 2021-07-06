package com.artyomefimov.expensescontrol.di

import android.content.Context
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
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
        clock: Clock,
        dataStore: DataStore<Preferences>,
    ): WorkerFactory = ExpensesWorkerFactory(
        expenseInteractor,
        notificationBuilder,
        clock,
        dataStore
    )

    @Provides
    @Singleton
    fun provideWorkManagerConfiguration(
        workerFactory: WorkerFactory,
    ): Configuration = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            migrations = providesMigrations(context)
        ) {
            context.preferencesDataStoreFile(PREFS_FILE_NAME)
        }
    }

    @Provides
    @Singleton
    fun providesMigrations(
        @ApplicationContext context: Context
    ): List<DataMigration<Preferences>> = listOf(
        SharedPreferencesMigration(
            context = context,
            sharedPreferencesName = PREFS_FILE_NAME,
        )
    )
}
