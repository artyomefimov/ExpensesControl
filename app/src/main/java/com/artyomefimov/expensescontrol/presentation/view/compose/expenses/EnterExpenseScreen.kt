package com.artyomefimov.expensescontrol.presentation.view.compose.expenses

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.presentation.model.expense.ExpenseEvent
import com.artyomefimov.expensescontrol.presentation.view.compose.LocalSnackbarHostState
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.Toolbar
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.chip.ChipGroup
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.edittext.ClearableTextField
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.edittext.MoneyTextField
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.list.ExpenseInfoItem
import com.artyomefimov.expensescontrol.presentation.viewmodel.expenses.EnterExpenseViewModel
import java.util.*
import kotlin.random.Random

/**
 * Экран добавления трат
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun EnterExpenseScreen(
    modifier: Modifier = Modifier,
    viewModel: EnterExpenseViewModel = hiltViewModel(),
) {
    val scrollState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    viewModel.expenseScreenEvent.value.getContent()?.let { event ->
        when (event) {
            is ExpenseEvent.MessageEvent -> {
                val snackbarHostState = LocalSnackbarHostState.current
                LaunchedEffect(key1 = Random.nextInt()) {
                    snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }

    Column {
        Toolbar(
            textResId = R.string.enter_expense_screen_title,
            iconResId = R.drawable.ic_apply,
            onToolbarActionPressed = {
                viewModel.addExpense()
                focusManager.clearFocus()
            }
        )
        CustomAnimatedVisibility(visible = scrollState.isScrollingUp()) {
            MoneyTextField(
                placeholderTextResId = R.string.enter_field_hint,
                value = viewModel.amountState.value,
                onTextChanged = viewModel::updateAmount,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 32.dp,
                    )
                    .then(
                        if (scrollState.firstVisibleItemIndex != 0) Modifier.width(0.dp) else Modifier.fillMaxWidth()
                    ),
            )
        }
        CustomAnimatedVisibility(visible = scrollState.isScrollingUp()) {
            ClearableTextField(
                placeholderTextResId = R.string.comment_field_hint,
                text = viewModel.commentState.value,
                onTextChanged = viewModel::updateComment,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 32.dp,
                    ),
            )
        }
        CustomAnimatedVisibility(visible = scrollState.isScrollingUp()) {
            ChipGroup(
                modifier = Modifier
                    .padding(top = 16.dp),
                items = viewModel.allCategories,
                selectedItems = viewModel.selectedCategoryState.value?.let { listOf(it) }
                    ?: emptyList(),
                onSelectedItemChanged = viewModel::updateSelectedCategory
            )
        }
        LazyColumn(
            modifier = modifier.wrapContentSize(Alignment.Center),
            state = scrollState,
        ) {
            stickyHeader {
                StickyInformation(viewModel = viewModel)
            }
            items(items = viewModel.currentMonthExpensesState) { expense ->
                ExpenseInfoItem(expense = expense)
            }
        }
    }
}

@Composable
private fun StickyInformation(
    viewModel: EnterExpenseViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            text = viewModel.availableSumState.value.availableDailySum,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            text = viewModel.availableSumState.value.availableMonthlySum,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
        )
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    return firstVisibleItemIndex == 0 && layoutInfo.viewportStartOffset == 0
}
