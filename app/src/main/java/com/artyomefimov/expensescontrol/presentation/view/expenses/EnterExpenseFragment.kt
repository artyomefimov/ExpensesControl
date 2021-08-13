package com.artyomefimov.expensescontrol.presentation.view.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.databinding.FragmentEnterExpenseBinding
import com.artyomefimov.expensescontrol.presentation.ext.*
import com.artyomefimov.expensescontrol.presentation.model.AvailableSumInfo
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import com.artyomefimov.expensescontrol.presentation.view.edittext.MoneyTextWatcher
import com.artyomefimov.expensescontrol.presentation.view.recyclerview.ExpensesAdapter
import com.artyomefimov.expensescontrol.presentation.view.recyclerview.ExpensesDiffUtilCallback
import com.artyomefimov.expensescontrol.presentation.viewmodel.expenses.EnterExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Экран, на котором происходит добавление траты, выбор категории, отображение
 * списка трат за текущий месяц
 */
@AndroidEntryPoint
class EnterExpenseFragment : Fragment() {

    private lateinit var binding: FragmentEnterExpenseBinding
    private lateinit var adapter: ExpensesAdapter
    private val viewModel: EnterExpenseViewModel by viewModels()
    private lateinit var textWatcher: MoneyTextWatcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterExpenseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.isBackButtonVisible = false
        adapter = ExpensesAdapter()
        binding.expensesRecyclerView.adapter = adapter
        binding.toolbar.onIconPressedListener = {
            viewModel.addExpense(
                stringSum = binding.enterSumEditText.text?.toString()?.formatToAmount(),
                comment = binding.commentEditText.text?.toString(),
                category = binding.categoriesGroup.getSelectedCategory()
            )
        }
        binding.enterSumEditText.apply {
            textWatcher = MoneyTextWatcher(this, fractionFormatter)
            addTextChangedListener(textWatcher)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.availableDailySumState().safeObserve(this, ::updateAvailableSum)
        viewModel.currentMonthExpensesState().safeObserve(this, ::updateExpenses)
        viewModel.navigateToEnterIncomeScreen().observeEvent(this) {
            navigateToEnterIncomeFragment()
        }
        viewModel.showSnackbar().observeEvent(this) {
            showSnackbar()
        }
        viewModel.incorrectSum().observeEvent(this) {
            binding.root.showSnackbar(R.string.incorrect_sum)
        }
    }

    private fun updateAvailableSum(info: AvailableSumInfo) {
        binding.availableDailyTextView.text = info.availableDailySum
        binding.availableMonthlyTextView.text = info.availableMonthlySum
        if (info.isInitial.not()) {
            val updateSumAnimation = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shake_anim
            )
            binding.availableDailyTextView.startAnimation(updateSumAnimation)
        }
        clear()
    }

    private fun updateExpenses(items: List<ExpenseInfo>) {
        val callback = ExpensesDiffUtilCallback(
            oldItems = adapter.getItems(),
            newItems = items
        )
        val diffResult = DiffUtil.calculateDiff(callback)
        adapter.swapData(items)
        diffResult.dispatchUpdatesTo(adapter)
        binding.expensesRecyclerView.smoothScrollToPosition(0)
    }

    private fun navigateToEnterIncomeFragment() {
        binding.root.hideKeyboard()
        findNavController().navigate(R.id.action_expensesFragment_to_enterIncomeFragment)
    }

    private fun showSnackbar() {
        binding.root.showSnackbar(R.string.absent_expense_parameters_message)
    }

    private fun clear() = with(binding) {
        enterSumEditText.apply {
            text?.clear()
            clearFocus()
        }
        commentEditText.apply {
            text?.clear()
            clearFocus()
        }
        categoriesGroup.clearCheck()
        root.hideKeyboard()
    }
}
