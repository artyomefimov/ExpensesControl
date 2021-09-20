package com.artyomefimov.expensescontrol.presentation.view.compose.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.presentation.model.splash.SplashDestination
import com.artyomefimov.expensescontrol.presentation.viewmodel.splash.SplashViewModel

/**
 * Сплэш скрин на compose
 */
@Composable
fun SplashScreen(
    navigateToExpenseAction: () -> Unit,
    navigateToIncomeAction: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val destination = viewModel.navigationDestinationState().value
    when (destination) {
        SplashDestination.INCOME -> navigateToIncomeAction()
        SplashDestination.EXPENSES -> navigateToExpenseAction()
        SplashDestination.NONE -> Unit
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background),
    ) {
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(id = R.drawable.ic_money_main),
            contentDescription = "",
        )
        if (destination == SplashDestination.NONE) {
            CircularProgressIndicator(
                modifier = modifier
                    .size(48.dp)
                    .padding(top = 24.dp),
                color = MaterialTheme.colors.primary,
            )
        }
    }
}
