package com.artyomefimov.expensescontrol.presentation.viewmodel.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artyomefimov.expensescontrol.domain.interactor.income.IncomeInteractor
import com.artyomefimov.expensescontrol.domain.model.income.isZeroAndShouldBeEntered
import com.artyomefimov.expensescontrol.presentation.model.splash.SplashDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val incomeInteractor: IncomeInteractor,
) : ViewModel() {

    private companion object {
        const val DELAY_TIME = 1000L
    }

    private var navigationDestinationState = mutableStateOf(SplashDestination.NONE)

    fun navigationDestinationState(): State<SplashDestination> = navigationDestinationState

    init {
        initialCheck()
    }

    private fun initialCheck() = viewModelScope.launch {
        navigationDestinationState.value = if (shouldEnterIncome()) {
            SplashDestination.INCOME
        } else {
            SplashDestination.EXPENSES
        }
    }

    private suspend fun shouldEnterIncome(): Boolean {
        delay(DELAY_TIME)
        return incomeInteractor.getIncomeForCurrentMonth()
            .isZeroAndShouldBeEntered()
    }
}
