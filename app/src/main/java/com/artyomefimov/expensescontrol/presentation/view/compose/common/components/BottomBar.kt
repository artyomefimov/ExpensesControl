package com.artyomefimov.expensescontrol.presentation.view.compose.common.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.presentation.view.compose.common.navigation.Routes

private val bottomBarImageSize = 24.dp

/**
 * Бар для нижней навигации по приложению
 *
 * @param onExpensesClicked   действие по нажатию на item трат
 * @param onStatisticsClicked действие по нажатию на item статистики
 */
@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    onExpensesClicked: (String) -> Unit,
    onStatisticsClicked: (String) -> Unit,
) {
    var isExpensesSelected: Boolean by remember { mutableStateOf(true) }

    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        BottomNavigationItem(
            selected = isExpensesSelected,
            alwaysShowLabel = true,
            icon = {
                Icon(
                    modifier = Modifier.size(bottomBarImageSize),
                    painter = painterResource(
                        id = NavigationItem.Expenses.icon
                    ),
                    contentDescription = stringResource(
                        id = NavigationItem.Expenses.title
                    ),
                )
            },
            label = {
                Text(
                    text = stringResource(
                        id = NavigationItem.Expenses.title
                    )
                )
            },
            onClick = {
                if (isExpensesSelected.not()) {
                    isExpensesSelected = isExpensesSelected.not()
                    onExpensesClicked(Routes.ExpensesRoute)
                }
            }
        )
        BottomNavigationItem(
            selected = isExpensesSelected.not(),
            alwaysShowLabel = true,
            icon = {
                Icon(
                    modifier = Modifier.size(bottomBarImageSize),
                    painter = painterResource(
                        id = NavigationItem.Statistics.icon
                    ),
                    contentDescription = stringResource(
                        id = NavigationItem.Statistics.title
                    ),
                )
            },
            label = {
                Text(
                    text = stringResource(
                        id = NavigationItem.Statistics.title
                    ),
                    style = MaterialTheme.typography.caption,
                )
            },
            onClick = {
                if (isExpensesSelected) {
                    isExpensesSelected = isExpensesSelected.not()
                    onStatisticsClicked(Routes.StatisticsRoute)
                }
            }
        )
    }
}

sealed class NavigationItem(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
) {

    object Expenses : NavigationItem(
        icon = R.drawable.ic_expenses,
        title = R.string.expenses_label,
    )

    object Statistics : NavigationItem(
        icon = R.drawable.ic_statistics,
        title = R.string.statistics_label,
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
fun BottomNavigationBarPreview() {
    BottomNavigationBar(
        onExpensesClicked = {},
        onStatisticsClicked = {},
    )
}
