package com.artyomefimov.expensescontrol.presentation.view.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.BottomNavigationBar
import com.artyomefimov.expensescontrol.presentation.view.compose.common.navigation.ExpensesControlNavHost
import com.artyomefimov.expensescontrol.presentation.view.compose.common.navigation.Routes
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val LocalSnackbarHostState =
    compositionLocalOf<SnackbarHostState> { error("No host state available") }

@Composable
fun ExpensesControlApp() {
    rememberSystemUiController().ColorStatusBar()
    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backstackEntry.value?.destination?.route
    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                if (isBottomBarVisible(currentRoute)) {
                    BottomNavigationBar(
                        onExpensesClicked = { route ->
                            navController.navigate(route)
                        },
                        onStatisticsClicked = { route ->
                            navController.navigate(route)
                        },
                    )
                }
            }
        ) { innerPadding ->
            ExpensesControlNavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
            )
        }
    }
}

@Composable
private fun SystemUiController.ColorStatusBar() {
    val color = MaterialTheme.colors.primaryVariant
    SideEffect {
        setStatusBarColor(
            color = color,
        )
    }
}

private fun isBottomBarVisible(route: String?): Boolean {
    return when (route) {
        Routes.ExpensesRoute,
        Routes.StatisticsRoute -> true
        Routes.ChartRoute,
        Routes.SplashRoute,
        Routes.IncomeRoute -> false
        null -> false
        else -> error("Incorrect route $route")
    }
}
