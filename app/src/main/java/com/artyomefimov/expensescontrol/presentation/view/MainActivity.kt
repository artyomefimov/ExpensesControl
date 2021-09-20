package com.artyomefimov.expensescontrol.presentation.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.artyomefimov.expensescontrol.presentation.view.compose.ExpensesControlApp
import com.artyomefimov.expensescontrol.presentation.view.compose.theme.ExpensesControlTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExpensesControlTheme {
                ExpensesControlApp()
            }
        }
    }
}
