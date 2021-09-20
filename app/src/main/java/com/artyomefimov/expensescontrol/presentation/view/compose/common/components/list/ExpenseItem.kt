package com.artyomefimov.expensescontrol.presentation.view.compose.common.components.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo

/**
 * Элемент списка трат, отображающий информацию [ExpenseInfo]
 *
 * @param expense информация для отображения
 */
@Composable
fun ExpenseInfoItem(
    modifier: Modifier = Modifier,
    expense: ExpenseInfo,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colors.surface,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                FieldInfo(text = expense.category, textOverflow = TextOverflow.Ellipsis)
                FieldInfo(text = expense.comment, textOverflow = TextOverflow.Ellipsis)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 16.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                FieldInfo(text = expense.sum)
                FieldInfo(text = expense.timestamp)
            }
        }
    }
}

@Composable
private fun FieldInfo(
    text: String,
    textOverflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onSurface,
        overflow = textOverflow,
    )
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
    val info = ExpenseInfo(
        id = 0, sum = "Sum", comment = "Comment", category = "Category", timestamp = "Timestamp"
    )
    ExpenseInfoItem(expense = info)
}
