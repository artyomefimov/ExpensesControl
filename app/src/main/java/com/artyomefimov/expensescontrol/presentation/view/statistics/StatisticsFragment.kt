package com.artyomefimov.expensescontrol.presentation.view.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.artyomefimov.expensescontrol.databinding.FragmentStatisticsBinding
import com.artyomefimov.expensescontrol.infrastructure.showSnackbar
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import com.artyomefimov.expensescontrol.presentation.model.FilterModel
import com.artyomefimov.expensescontrol.presentation.model.FilterType
import com.artyomefimov.expensescontrol.presentation.view.recyclerview.ExpensesAdapter
import com.artyomefimov.expensescontrol.presentation.view.recyclerview.ExpensesDiffUtilCallback
import com.artyomefimov.expensescontrol.presentation.view.statistics.dialogs.showCategoryDialog
import com.artyomefimov.expensescontrol.presentation.view.statistics.dialogs.showPeriodSelectDialog
import com.artyomefimov.expensescontrol.presentation.viewmodel.statistics.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment: Fragment() {

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
    }

    private fun applyFilterModel(model: FilterModel) = with(model) {
        binding.chipPeriod.isChecked = periodFilterEnabled
        binding.chipCategory.isChecked = categoryFilterEnabled
        binding.chipMaxSum.isChecked = maxSumFilterEnabled
    }

    private fun showPeriodDialog() {
        showPeriodSelectDialog { from, to -> viewModel.setPeriodFilter(from, to) }
    }

    private fun showCategoryDialog(items: Array<String>) {
        requireContext().showCategoryDialog(items) {
            binding.root.showSnackbar(it)
        }
    }

    private fun showCategory(category: String) {
        binding.chipCategory.text = category
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
}