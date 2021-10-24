package com.artyomefimov.expensescontrol.presentation.view.compose.statistics

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.artyomefimov.expensescontrol.presentation.viewmodel.statistics.StatisticsViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend

private const val CHART_INITIAL_ANIMATION_DURATION = 1400

/**
 * Диалоговое окно с графиком статистики трат по категорям
 */
@Composable
fun ChartDialog(
    modifier: Modifier = Modifier,
    viewModel: StatisticsViewModel = hiltViewModel(),
) {
    val halfOfScreen = LocalConfiguration.current.screenHeightDp / 2

    if (viewModel.dialogState.value) {
        Dialog(onDismissRequest = { viewModel.hideChart() }) {
            Surface(
                modifier = modifier.requiredHeight(halfOfScreen.dp),
                color = MaterialTheme.colors.surface
            ) {
                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { context ->
                        PieChart(context).apply {
                            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                            isDrawHoleEnabled = true
                            description.isEnabled = false
                            setDrawEntryLabels(false)
                            data = viewModel.chartData?.data
                            invalidate()
                            animateY(
                                CHART_INITIAL_ANIMATION_DURATION,
                                Easing.EaseInOutQuad
                            )
                        }
                    }
                )
            }
        }
    }
}
