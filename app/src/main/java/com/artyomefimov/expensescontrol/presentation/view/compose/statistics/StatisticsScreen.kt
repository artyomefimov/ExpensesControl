package com.artyomefimov.expensescontrol.presentation.view.compose.statistics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.presentation.model.statistics.StatisticsEvent
import com.artyomefimov.expensescontrol.presentation.view.MainActivity
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.Toolbar
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.chip.ChipGroup
import com.artyomefimov.expensescontrol.presentation.view.compose.common.components.list.ExpensesList
import com.artyomefimov.expensescontrol.presentation.view.dialogs.showCategoryDialog
import com.artyomefimov.expensescontrol.presentation.view.dialogs.showPeriodSelectDialog
import com.artyomefimov.expensescontrol.presentation.viewmodel.statistics.StatisticsViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticsViewModel = hiltViewModel(),
) {
    val expenses = viewModel.suitableExpensesState

    HandleEvent(viewModel = viewModel)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Toolbar(
            textResId = R.string.statistics_screen_title,
            iconResId = R.drawable.ic_pie_chart,
            isIconVisible = viewModel.chartAvailabilityState.value,
            onToolbarActionPressed = {
                viewModel.prepareDataForChart()
            },
        )
        ChipGroup(
            modifier = Modifier.padding(top = 16.dp),
            items = viewModel.filters,
            selectedItems = viewModel.selectedFiltersState,
            onSelectedItemChanged = viewModel::setFilter,
        )
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            visible = viewModel.commonSumState.value != null
        ) {
            Text(
                text = viewModel.commonSumState.value.orEmpty(),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
            )
        }
        AnimatedVisibility(visible = expenses.isNotEmpty()) {
            ExpensesList(expenses = expenses)
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            visible = expenses.isEmpty()
        ) {
            Text(
                text = stringResource(id = R.string.no_matching_items),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
            )
        }
    }
}

@Composable
private fun HandleEvent(
    viewModel: StatisticsViewModel,
) {
    viewModel.statisticsViewEvent.value.getContent()?.let { event ->
        when(event) {
            is StatisticsEvent.OpenCategoryDialog -> {
                LocalContext.current.showCategoryDialog(
                    items = event.categories,
                    onCategorySelected = { selectedCategory ->
                        viewModel.setCategoryFilter(selectedCategory)
                    },
                )
            }
            is StatisticsEvent.OpenChartScreen -> {
                ChartDialog()
            }
            is StatisticsEvent.OpenPeriodDialog -> {
                (LocalContext.current as MainActivity).supportFragmentManager.showPeriodSelectDialog(
                    onPeriodSelected =  { from, to -> viewModel.setPeriodFilter(from, to) }
                )
            }
        }
    }
}
