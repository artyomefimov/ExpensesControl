package com.artyomefimov.expensescontrol.presentation.view.expenses

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.databinding.FragmentEnterExpenseBinding
import com.artyomefimov.expensescontrol.presentation.ext.observeEvent
import com.artyomefimov.expensescontrol.presentation.ext.safeObserve
import com.artyomefimov.expensescontrol.presentation.model.AvailableSumInfo
import com.artyomefimov.expensescontrol.presentation.viewmodel.EnterExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterExpenseFragment : Fragment() {

    private lateinit var binding: FragmentEnterExpenseBinding
    private val viewModel: EnterExpenseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentEnterExpenseBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.enter_expense_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.apply_expense_item) {
            viewModel.addExpense(
                stringSum = binding.enterSumEditText.text?.toString(),
                comment = binding.commentEditText.text?.toString()
            )
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.availableDailySumState().safeObserve(this, ::updateAvailableSum)
        viewModel.navigateToEnterIncomeScreen().observeEvent(this) {
            navigateToEnterIncomeFragment()
        }
    }

    private fun updateAvailableSum(info: AvailableSumInfo) {
        binding.moneyLeftTextView.text = info.availableSum
        if (info.isInitial.not()) {
            val updateSumAnimation = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shake_and_explode
            )
            binding.moneyLeftTextView.startAnimation(updateSumAnimation)
        }
        binding.enterSumEditText.text?.clear()
    }

    private fun navigateToEnterIncomeFragment() {
        findNavController().navigate(R.id.action_expensesFragment_to_enterIncomeFragment)
    }
}