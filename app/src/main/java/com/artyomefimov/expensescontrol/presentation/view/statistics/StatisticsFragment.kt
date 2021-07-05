package com.artyomefimov.expensescontrol.presentation.view.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.artyomefimov.expensescontrol.databinding.FragmentStatisticsBinding
import com.artyomefimov.expensescontrol.domain.model.statistics.StatisticsFilter
import com.artyomefimov.expensescontrol.presentation.ext.observeEvent
import com.artyomefimov.expensescontrol.presentation.ext.safeObserve
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import com.artyomefimov.expensescontrol.presentation.model.FilterType
import com.artyomefimov.expensescontrol.presentation.view.recyclerview.ExpensesAdapter
import com.artyomefimov.expensescontrol.presentation.view.recyclerview.ExpensesDiffUtilCallback
import com.artyomefimov.expensescontrol.presentation.view.statistics.dialogs.showCategoryDialog
import com.artyomefimov.expensescontrol.presentation.view.statistics.dialogs.showPeriodSelectDialog
import com.artyomefimov.expensescontrol.presentation.viewmodel.statistics.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Экран, на котором происходит фильтрация трат по доступным фильтрам для
 * отображении статистики, необходимой пользователю
 */
@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var adapter: ExpensesAdapter
    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.chipPeriod.setOnClickListener {
            viewModel.setFilter(FilterType.PERIOD)
        }
        binding.chipCategory.setOnClickListener {
            viewModel.setFilter(FilterType.CATEGORY)
        }
        binding.chipMaxSum.setOnClickListener {
            viewModel.setFilter(FilterType.MAX_SUM)
        }
        adapter = ExpensesAdapter()
        binding.expensesRecyclerView.adapter = adapter

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.currentFiltersState().safeObserve(this, ::applyCurrentFilters)
        viewModel.selectedCategoryState().safeObserve(this, ::showCategory)
        viewModel.suitableExpensesState().safeObserve(this, ::updateExpenses)
        viewModel.commonSumState().observe(viewLifecycleOwner, ::showCommonSum)
        viewModel.showPeriodDialogViewEvent().observeEvent(this) {
            showPeriodDialog()
        }
        viewModel.showCategoryDialogViewEvent().observeEvent(this) { categories ->
            showCategoryDialog(categories)
        }
    }

    private fun applyCurrentFilters(model: StatisticsFilter) = with(model) {
        binding.chipPeriod.isChecked = model.periodFilter != null
        binding.chipCategory.isChecked = model.categoryFilter != null
        binding.chipMaxSum.isChecked = model.isMaxSumFilterEnabled
    }

    private fun showCategory(category: String) {
        binding.chipCategory.text = category
    }

    private fun updateExpenses(items: List<ExpenseInfo>) {
        binding.noMatchingItemsTextView.isVisible = items.isEmpty()
        binding.expensesRecyclerView.isVisible = items.isNotEmpty()
        val callback = ExpensesDiffUtilCallback(
            oldItems = adapter.getItems(),
            newItems = items
        )
        val diffResult = DiffUtil.calculateDiff(callback)
        adapter.swapData(items)
        diffResult.dispatchUpdatesTo(adapter)
        binding.expensesRecyclerView.smoothScrollToPosition(0)
    }

    private fun showCommonSum(commonSum: String?) =
        with(binding.commonSumOfFilteredExpensesTextView) {
            isVisible = commonSum != null
            text = commonSum.orEmpty()
        }

    private fun showPeriodDialog() {
        showPeriodSelectDialog(
            onPeriodSelected =  { from, to -> viewModel.setPeriodFilter(from, to) },
            onCancel = { binding.chipPeriod.isChecked = binding.chipPeriod.isChecked.not() }
        )
    }

    private fun showCategoryDialog(items: Array<String>) {
        requireContext().showCategoryDialog(
            items = items,
            onCategorySelected = { selectedCategory ->
                viewModel.setCategoryFilter(selectedCategory)
            },
            onCancel = { binding.chipCategory.isChecked = binding.chipCategory.isChecked.not() }
        )
    }
}
