package com.artyomefimov.expensescontrol.presentation.view.compose.common.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artyomefimov.expensescontrol.R

private val toolbarHeight = 56.dp
private val toolbarImageSize = 24.dp

/**
 * Общий тулбар приложения. Имеет текст и иконку экшена
 *
 * @param textResId              id ресурса для отображения текста
 * @param iconResId              id иконка экшена
 * @param isIconVisible          true, если иконка видима, false - иначе
 * @param onToolbarActionPressed действие по нажатию на иконку экшена
 */
@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    @StringRes textResId: Int,
    @DrawableRes iconResId: Int,
    isIconVisible: Boolean = true,
    onToolbarActionPressed: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .height(toolbarHeight)
            .fillMaxWidth(),
        color = MaterialTheme.colors.primary,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    )
                    .align(Alignment.CenterVertically),
                text = stringResource(textResId),
                style = MaterialTheme.typography.h6,
            )
            if (isIconVisible) {
                Box(
                    modifier = Modifier
                        .size(toolbarHeight)
                        .clip(CircleShape)
                        .clickable { onToolbarActionPressed() }
                ) {
                    Icon(
                        painter = painterResource(iconResId),
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(toolbarImageSize)
                    )
                }
            }
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
fun ToolbarPreview() {
    Toolbar(
        textResId = R.string.app_name,
        iconResId = R.drawable.ic_apply,
    )
}
