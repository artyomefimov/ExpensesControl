package com.artyomefimov.expensescontrol.presentation.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.artyomefimov.expensescontrol.presentation.model.ChartDataUi

class StatisticsChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val oval = RectF(200F, 200F, 450F, 450F)
    private val paint = Paint()
    private var startPosition = 0f

    var chartData: ChartDataUi? = null
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        chartData?.data?.entries?.forEach {
            paint.color = getColor()
            canvas.drawArc(oval, startPosition, it.value, true, paint)
            startPosition += it.value
        }
        startPosition = 0f
    }

    private fun getColor(): Int {
        return listOf(Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW)
            .shuffled()
            .first()
    }
}
