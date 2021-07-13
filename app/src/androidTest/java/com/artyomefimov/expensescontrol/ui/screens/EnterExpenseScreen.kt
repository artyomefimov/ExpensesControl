package com.artyomefimov.expensescontrol.ui.screens

import android.view.View
import com.agoda.kakao.chipgroup.KChipGroup
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.artyomefimov.expensescontrol.R
import org.hamcrest.Matcher

object EnterExpenseScreen : Screen<EnterExpenseScreen>() {
    val enterSumEditText = KEditText { withId(R.id.enterSumEditText) }
    val enterCommentEditText = KEditText { withId(R.id.commentEditText) }
    val categoriesGroup = KChipGroup { withId(R.id.categoriesGroup) }
    val expensesRecyclerView = KRecyclerView(
        builder = { withId(R.id.expensesRecyclerView) },
        itemTypeBuilder = { itemType(::ExpenseItem) },
    )
    val toolbarIconButton = KView { withId(R.id.toolbarIconButton) }
    val snackbarView = KView { withId(com.google.android.material.R.id.snackbar_text) }

    class ExpenseItem(parent: Matcher<View>) : KRecyclerItem<ExpenseItem>(parent) {
        val categoryTextView: KTextView = KTextView(parent) { withId(R.id.categoryTextView) }
        val commentTextView: KTextView = KTextView(parent) { withId(R.id.commentTextView) }
        val sumTextView: KTextView = KTextView(parent) { withId(R.id.sumTextView) }
    }
}
