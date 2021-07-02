package com.artyomefimov.expensescontrol.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artyomefimov.expensescontrol.presentation.view.MainActivity
import com.artyomefimov.expensescontrol.ui.scenario.EnterExpenseScenario
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest : TestCase() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Ignore("Ignored in CI. Should be run manually")
    @Test
    fun expensesScreenTest() {
        before {

        }.after {

        }.run {
            scenario(EnterExpenseScenario())
        }
    }
}
