package com.artyomefimov.expensescontrol.di

import android.content.Context
import androidx.room.Room
import com.artyomefimov.expensescontrol.data.db.DB_NAME
import com.artyomefimov.expensescontrol.data.db.ExpensesDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext context: Context
    ): ExpensesDb = Room.databaseBuilder(
        context,
        ExpensesDb::class.java,
        DB_NAME
    ).build()
}
