package com.artyomefimov.expensescontrol.presentation.view.expenses

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.databinding.FragmentEnterIncomeBinding
import com.artyomefimov.expensescontrol.presentation.ext.observeEvent
import com.artyomefimov.expensescontrol.presentation.viewmodel.EnterIncomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterIncomeFragment : Fragment() {

    private lateinit var binding: FragmentEnterIncomeBinding
    private val viewModel: EnterIncomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentEnterIncomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.enter_income_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.apply_income_item) {
            viewModel.addIncome(binding.enterMoneyEditText.text?.toString())
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.navigateToExpenseScreen().observeEvent(this) {
            findNavController().navigate(R.id.expenses_dest)
        }
    }
}