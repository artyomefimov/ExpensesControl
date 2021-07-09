package com.artyomefimov.expensescontrol.presentation.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class StatisticsChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private val oval = RectF(200F, 200F, 450F, 450F)
    override fun onDraw(canvas: Canvas) {
//        canvas.drawArc(oval, 0F, 90F, true, paint)
    }
}
