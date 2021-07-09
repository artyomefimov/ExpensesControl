package com.artyomefimov.expensescontrol.di

import com.artyomefimov.expensescontrol.data.mapper.ExpenseFromEntityMapper
import com.artyomefimov.expensescontrol.data.mapper.ExpenseToEntityMapper
import com.artyomefimov.expensescontrol.data.model.ExpenseEntity
import com.artyomefimov.expensescontrol.data.repo.expense.ExpenseRepositoryImpl
import com.artyomefimov.expensescontrol.data.repo.income.IncomeRepositoryImpl
import com.artyomefimov.expensescontrol.domain.interactor.expense.ExpenseInteractor
import com.artyomefimov.expensescontrol.domain.interactor.expense.ExpenseInteractorImpl
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractor
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractorImpl
import com.artyomefimov.expensescontrol.domain.interactor.statistics.ChartInteractor
import com.artyomefimov.expensescontrol.domain.interactor.statistics.ChartInteractorImpl
import com.artyomefimov.expensescontrol.domain.interactor.statistics.StatisticsInteractor
import com.artyomefimov.expensescontrol.domain.interactor.statistics.StatisticsInteractorImpl
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.model.statistics.chart.ChartData
import com.artyomefimov.expensescontrol.domain.repo.expense.ExpenseRepository
import com.artyomefimov.expensescontrol.domain.repo.income.IncomeRepository
import com.artyomefimov.expensescontrol.infrastructure.NotificationBuilder
import com.artyomefimov.expensescontrol.infrastructure.NotificationBuilderImpl
import com.artyomefimov.expensescontrol.presentation.mapper.ChartDataMapper
import com.artyomefimov.expensescontrol.presentation.mapper.ExpenseInfoMapper
import com.artyomefimov.expensescontrol.presentation.model.ChartDataUi
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import com.artyomefimov.expensescontrol.presentation.resources.ResourcesProvider
import com.artyomefimov.expensescontrol.presentation.resources.ResourcesProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    @Singleton
    abstract fun incomeRepository(
        repo: IncomeRepositoryImpl
    ): IncomeRepository

    @Binds
    @Singleton
    abstract fun expenseRepository(
        repo: ExpenseRepositoryImpl
    ): ExpenseRepository

    @Binds
    @Singleton
    abstract fun incomeInteractor(
        interactor: IncomeInteractorImpl
    ): IncomeInteractor

    @Binds
    @Singleton
    abstract fun dailyExpenseInteractor(
        interactor: ExpenseInteractorImpl
    ): ExpenseInteractor

    @Binds
    @Singleton
    abstract fun statisticsInteractor(
        interactor: StatisticsInteractorImpl
    ): StatisticsInteractor

    @Binds
    @Singleton
    abstract fun chartInteractor(
        interactor: ChartInteractorImpl
    ): ChartInteractor

    @Binds
    @Singleton
    abstract fun resourcesProvider(
        provider: ResourcesProviderImpl
    ): ResourcesProvider

    @Binds
    @Singleton
    abstract fun expenseFromEntityMapper(
        mapper: ExpenseFromEntityMapper
    ): Mapper<ExpenseEntity, Expense>

    @Binds
    @Singleton
    abstract fun expenseToEntityMapper(
        mapper: ExpenseToEntityMapper
    ): Mapper<Expense, ExpenseEntity>

    @Binds
    @Singleton
    abstract fun expenseInfoMapper(
        mapper: ExpenseInfoMapper
    ): Mapper<Expense, ExpenseInfo>

    @Binds
    @Singleton
    abstract fun chartDataMapper(
        mapper: ChartDataMapper
    ): Mapper<ChartData, ChartDataUi>

    @Binds
    @Singleton
    abstract fun notificationBuilder(
        impl: NotificationBuilderImpl
    ): NotificationBuilder
}
