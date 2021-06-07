package com.artyomefimov.expensescontrol.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.artyomefimov.expensescontrol.data.db.DB_NAME
import com.artyomefimov.expensescontrol.data.db.ExpensesDb
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
    ): SharedPreferences {
        return context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideClock(): Clock = Clock.System

    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext context: Context
    ): ExpensesDb {
        return Room.databaseBuilder(
            context,
            ExpensesDb::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}