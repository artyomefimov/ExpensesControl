package com.artyomefimov.expensescontrol.presentation.view.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.artyomefimov.expensescontrol.databinding.FragmentChartBinding
import com.artyomefimov.expensescontrol.presentation.viewmodel.statistics.StatisticsViewModel
import com.github.mikephil.charting.animation.Easing

class ChartFragment : Fragment() {

    private companion object {
        const val CHART_INITIAL_ANIMATION_DURATION = 1400
    }

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
        binding.chartView.apply {
            isDrawHoleEnabled = true
            description.isEnabled = false
            setDrawEntryLabels(false)
            data = viewModel.chartData?.data
            invalidate()
            animateY(
                CHART_INITIAL_ANIMATION_DURATION,
                Easing.EaseInOutQuad
            )
        }
    }
}
