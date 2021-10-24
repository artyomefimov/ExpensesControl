package com.artyomefimov.expensescontrol.presentation.view.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = Day.ColorPrimary,
    primaryVariant = Day.ColorPrimaryVariant,
    secondary = Day.ColorSecondary,
    surface = Day.ColorSurface,
    background = Day.ColorBackground,
    onPrimary = Day.ColorOnPrimary,
    onSecondary = Day.ColorOnSecondary,
    onSurface = Day.ColorOnSurface,
    onBackground = Day.ColorOnBackground,
)

private val DarkColorPalette = darkColors(
    primary = Night.ColorPrimary,
    primaryVariant = Night.ColorPrimaryVariant,
    secondary = Night.ColorSecondary,
    surface = Night.ColorSurface,
    background = Night.ColorBackground,
    onPrimary = Night.ColorOnPrimary,
    onSecondary = Night.ColorOnSecondary,
    onSurface = Night.ColorOnSurface,
    onBackground = Night.ColorOnBackground,
)

@Composable
fun ExpensesControlTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = MaterialTheme.typography,
        shapes = Shapes,
        content = content
    )
}
