package com.artyomefimov.expensescontrol.presentation.view.expenses.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    private val space: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = if (parent.getChildLayoutPosition(view) == 0) space else 0
        outRect.left = space
        outRect.bottom = space
        outRect.right = space
    }
}