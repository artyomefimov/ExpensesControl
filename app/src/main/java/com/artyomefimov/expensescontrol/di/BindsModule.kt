package com.artyomefimov.expensescontrol.di

import com.artyomefimov.expensescontrol.data.mapper.ExpenseFromEntityMapper
import com.artyomefimov.expensescontrol.data.mapper.ExpenseToEntityMapper
import com.artyomefimov.expensescontrol.data.model.ExpenseEntity
import com.artyomefimov.expensescontrol.data.repo.ExpenseRepositoryImpl
import com.artyomefimov.expensescontrol.data.repo.IncomeRepositoryImpl
import com.artyomefimov.expensescontrol.domain.interactor.dailyexpense.DailyExpenseInteractor
import com.artyomefimov.expensescontrol.domain.interactor.dailyexpense.DailyExpenseInteractorImpl
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractor
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractorImpl
import com.artyomefimov.expensescontrol.domain.repo.ExpenseRepository
import com.artyomefimov.expensescontrol.domain.repo.IncomeRepository
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.model.Expense
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
        interactor: DailyExpenseInteractorImpl
    ): DailyExpenseInteractor

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
}