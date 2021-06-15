package com.artyomefimov.expensescontrol.scenario

import com.agoda.kakao.screen.Screen
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.screens.EnterExpenseScreen
import com.kaspersky.kaspresso.testcases.api.scenario.BaseScenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

class EnterExpenseScenario<ScenarioData> : BaseScenario<ScenarioData>() {

    private companion object {
        const val sum = "1000"
        const val sumValue = "1000 ₽"
        const val comment = "comment"
    }

    override val steps: TestContext<ScenarioData>.() -> Unit = {
        step("Если не выбран ни один чип, показывается снекбар, трата не добавлена") {
            EnterExpenseScreen {
                enterSumEditText {
                    clearText()
                    typeText(sum)
                }
                enterCommentEditText {
                    clearText()
                    typeText(comment)
                }
                applyExpenseItem.click()

                snackbarView.isVisible()
            }
        }
        step("Ждем пока уйдет снекбар") {
            Screen.idle(duration = 3000L)
        }
        step("Если введена сумма и выбрана категория, трата добавляется") {
            EnterExpenseScreen {
                enterSumEditText {
                    clearText()
                    typeText(sum)
                }
                enterCommentEditText {
                    clearText()
                    typeText(comment)
                }
                categoriesGroup.selectChip(R.id.chipMandatory)
                applyExpenseItem.click()

                expensesRecyclerView.firstChild<EnterExpenseScreen.ExpenseItem> {
                    categoryTextView.hasText(R.string.category_mandatory_payments)
                    commentTextView.hasText(comment)
                    sumTextView.hasText(sumValue)
                }
            }
        }
    }
}