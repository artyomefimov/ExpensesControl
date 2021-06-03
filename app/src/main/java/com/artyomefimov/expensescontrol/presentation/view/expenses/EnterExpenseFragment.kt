package com.artyomefimov.expensescontrol.presentation.view.expenses

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.databinding.FragmentEnterExpenseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterExpenseFragment : Fragment(R.layout.fragment_enter_expense) {

    private lateinit var binding: FragmentEnterExpenseBinding

    private var sum = 100.0

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
            val expense = binding.enterSumLayout.editText?.text?.toString()?.toDouble() ?: 0.0
            sum -= expense
            val set = AnimationUtils.loadAnimation(requireContext(), R.anim.shake_and_explode)
            binding.moneyLeftTextView.text = sum.toString()
            binding.moneyLeftTextView.startAnimation(set)
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.moneyLeftTextView.text = "$sum ₽"
    }
}