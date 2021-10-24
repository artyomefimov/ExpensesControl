package com.artyomefimov.expensescontrol.presentation.view.compose.expenses

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable

private const val animationDuration = 500
private const val animationDelay = 500

@ExperimentalAnimationApi
val enterElementAnimation: EnterTransition = fadeIn(
    animationSpec = keyframes {
        durationMillis = animationDuration
        delayMillis = animationDelay
    }
) + expandVertically(
    animationSpec = keyframes {
        durationMillis = animationDuration
        delayMillis = animationDelay
    }
)

@ExperimentalAnimationApi
val exitElementAnimation: ExitTransition = fadeOut(
    animationSpec = keyframes {
        durationMillis = animationDuration
    }
) + shrinkVertically(
    animationSpec = keyframes {
        durationMillis = animationDuration
    }
)

@ExperimentalAnimationApi
@Composable
fun ColumnScope.CustomAnimatedVisibility(
    visible: Boolean,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = enterElementAnimation,
        exit = exitElementAnimation,
    ) {
        content()
    }
}
