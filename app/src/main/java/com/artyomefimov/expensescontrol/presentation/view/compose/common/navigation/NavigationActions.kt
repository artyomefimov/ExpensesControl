package com.artyomefimov.expensescontrol.presentation.view.compose.common.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

class NavigationActions(
    private val navController: NavHostController,
) {
    val navigateFromIncomeToExpense: () -> Unit = {
        navController.navigate(
            Routes.ExpensesRoute,
            NavOptions.Builder()
                .setPopUpTo(Routes.SplashRoute, true)
                .build(),
        )
    }

    val navigateFromExpenseToIncome: () -> Unit = {
        navController.navigate(
            Routes.IncomeRoute,
            NavOptions.Builder()
                .setPopUpTo(Routes.SplashRoute, true)
                .build(),
        )
    }
}
