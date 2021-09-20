package com.artyomefimov.expensescontrol.presentation.view.compose.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.artyomefimov.expensescontrol.presentation.view.compose.expenses.EnterExpenseScreen
import com.artyomefimov.expensescontrol.presentation.view.compose.expenses.EnterIncomeScreen
import com.artyomefimov.expensescontrol.presentation.view.compose.splash.SplashScreen
import com.artyomefimov.expensescontrol.presentation.view.compose.statistics.ChartDialog
import com.artyomefimov.expensescontrol.presentation.view.compose.statistics.StatisticsScreen

@Composable
fun ExpensesControlNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val navigationActions = remember(navController) { NavigationActions(navController) }
    val uri = "https://example.com"
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.SplashRoute,
    ) {
        composable(
            route = Routes.SplashRoute,
        ) {
            SplashScreen(
                navigateToExpenseAction = navigationActions.navigateFromIncomeToExpense,
                navigateToIncomeAction = navigationActions.navigateFromExpenseToIncome,
            )
        }
        composable(
            route = Routes.IncomeRoute,
        ) {
            EnterIncomeScreen(
                navigateToExpenseAction = navigationActions.navigateFromIncomeToExpense,
            )
        }
        composable(
            route = Routes.ExpensesRoute,
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/open_expenses" }),
        ) {
            EnterExpenseScreen()
        }
        composable(
            route = Routes.StatisticsRoute,
        ) {
            StatisticsScreen()
        }
    }
}
