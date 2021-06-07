package com.artyomefimov.expensescontrol.presentation.view

import android.content.Context
import android.util.AttributeSet
import com.artyomefimov.expensescontrol.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.math.roundToInt

class CategoryGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
): ChipGroup(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_category_group, this)
        isSingleSelection = true
        chipSpacingHorizontal = context.resources.getDimension(R.dimen.margin_8dp).roundToInt()
    }

    fun getSelectedCategory() = findViewById<Chip>(checkedChipId).text.toString()
}