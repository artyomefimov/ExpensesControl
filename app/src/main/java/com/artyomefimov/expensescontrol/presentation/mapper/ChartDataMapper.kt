package com.artyomefimov.expensescontrol.presentation.mapper

import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.model.statistics.chart.ChartData
import com.artyomefimov.expensescontrol.presentation.model.ChartDataUi
import javax.inject.Inject

class ChartDataMapper @Inject constructor(
): Mapper<ChartData, ChartDataUi> {

    override fun map(input: ChartData): ChartDataUi {
        val resultData = mutableMapOf<String, Float>()
        input.data.forEach { (category, percent) ->
            resultData[category] = percent.toFloat()
        }
        return ChartDataUi(
            data = resultData
        )
    }
}
