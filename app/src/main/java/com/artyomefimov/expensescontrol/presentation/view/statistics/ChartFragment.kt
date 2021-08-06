package com.artyomefimov.expensescontrol.presentation.view.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.artyomefimov.expensescontrol.databinding.FragmentChartBinding
import com.artyomefimov.expensescontrol.presentation.model.ChartDataUi
import com.artyomefimov.expensescontrol.presentation.viewmodel.statistics.StatisticsViewModel

class ChartFragment : Fragment() {

    private lateinit var binding: FragmentChartBinding
    private val viewModel: StatisticsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.apply {
            isIconVisible = false
            isBackButtonVisible = true
            onBackPressedListener = {
                findNavController().navigateUp()
            }
        }
        binding.chartView.chartData = viewModel.chartData
    }
}