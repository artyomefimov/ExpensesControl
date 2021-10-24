package com.artyomefimov.expensescontrol.presentation.view.compose.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.presentation.model.income.IncomeEvent
import com.artyomefimov.expensescontrol.presentation.view.compose.LocalSnackbarHostState
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.Toolbar
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.edittext.MoneyTextField
import com.artyomefimov.expensescontrol.presentation.viewmodel.expenses.EnterIncomeViewModel
import java.util.*
import kotlin.random.Random

/**
 * Экран ввода доступной суммы
 */
@Composable
fun EnterIncomeScreen(
    navigateToExpenseAction: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EnterIncomeViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    viewModel.incomeEvent().value.getContent()?.let { event ->
        when(event) {
            is IncomeEvent.NavigateToExpense -> {
                navigateToExpenseAction.invoke()
            }
            is IncomeEvent.ShowIncorrectSumMessage -> {
                val snackbarHostState = LocalSnackbarHostState.current
                LaunchedEffect(key1 = Random.nextInt()) {
                    snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }

    Column(
        modifier = modifier,
    ) {
        Toolbar(
            textResId = R.string.enter_income_screen_title,
            iconResId = R.drawable.ic_apply,
            onToolbarActionPressed = {
                viewModel.addIncome()
                focusManager.clearFocus()
            }
        )
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MoneyTextField(
                placeholderTextResId = R.string.enter_income_hint,
                value = viewModel.amountState().value,
                onTextChanged = viewModel::updateAmount,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 32.dp,
                    )
            )
            Text(
                text = stringResource(id = R.string.enter_income_explanation),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
