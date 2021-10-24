package com.artyomefimov.expensescontrol.presentation.mapper

import android.graphics.Color
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.model.statistics.chart.ChartData
import com.artyomefimov.expensescontrol.presentation.model.statistics.ChartDataUi
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import javax.inject.Inject

class ChartDataMapper @Inject constructor(
): Mapper<ChartData, ChartDataUi> {

    private companion object {
        const val chartEntryValueTextSize = 16f
        val allColors = listOf(
            Color.CYAN,
            Color.YELLOW,
            Color.RED,
            Color.DKGRAY,
            Color.GREEN,
            Color.parseColor("#ef5350"),
            Color.parseColor("#ff867c"),
            Color.parseColor("#73e8ff"),
            Color.parseColor("#ec407a"),
            Color.parseColor("#4d2c91"),
            Color.parseColor("#5c6bc0"),
            Color.parseColor("#ab47bc"),
            Color.parseColor("#df78ef"),
            Color.parseColor("#7e57c2"),
            Color.parseColor("#b085f5"),
            Color.parseColor("#26418f"),
            Color.parseColor("#8e99f3"),
            Color.parseColor("#80d6ff"),
            Color.parseColor("#42a5f5"),
            Color.parseColor("#29b6f6"),
            Color.parseColor("#d4e157"),
            Color.parseColor("#66bb6a"),
            Color.parseColor("#ffca28"),
        )
    }

    override fun map(input: ChartData): ChartDataUi {
        val entries = mutableListOf<PieEntry>()
        input.data.forEach { (category, percent) ->
            val model = PieEntry(percent.toFloat(), category)
            entries.add(model)
        }
        val pieDataSet = PieDataSet(entries, "").apply {
            valueTextSize = chartEntryValueTextSize
            colors = allColors
        }
        val pieData = PieData(pieDataSet).apply {
            setDrawValues(true)
        }
        return ChartDataUi(data = pieData)
    }
}
