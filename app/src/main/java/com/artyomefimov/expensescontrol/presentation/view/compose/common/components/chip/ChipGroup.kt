package com.artyomefimov.expensescontrol.presentation.view.compose.common.components.chip

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.artyomefimov.expensescontrol.presentation.model.ChipData
import com.artyomefimov.expensescontrol.presentation.model.expense.ExpensesChipData
import com.google.accompanist.flowlayout.FlowRow

/**
 * Представляет группу чипов, которые переносятся на новую строку при нехватке места в текущей
 *
 * @param items                 список данных для чипов
 * @param selectedItems         список информации выбранных чипов
 * @param onSelectedItemChanged действие по нажатию на чип
 */
@Composable
fun <T : ChipData> ChipGroup(
    items: List<T>,
    selectedItems: List<T>,
    onSelectedItemChanged: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier.padding(16.dp),
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp,
    ) {
        items.forEach { item ->
            Chip(
                data = item,
                isSelected = item in selectedItems,
                onClick = {
                    onSelectedItemChanged(it)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    showBackground = true,
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun ChipGroupPreview() {
    val items = listOf(
        ExpensesChipData(title = "sssssss"),
        ExpensesChipData(title = "aaaaaaaa"),
        ExpensesChipData(title = "bbbbbbb"),
        ExpensesChipData(title = "bbbbccccc"),
        ExpensesChipData(title = "ddddddd"),
        ExpensesChipData(title = "21sdsfdsgsg"),
    )
    ChipGroup(items = items, selectedItems = listOf(ExpensesChipData(title = "sssssss")), onSelectedItemChanged = {})
}
