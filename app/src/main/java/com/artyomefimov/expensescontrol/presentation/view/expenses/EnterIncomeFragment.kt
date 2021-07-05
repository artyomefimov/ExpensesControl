package com.artyomefimov.expensescontrol.presentation.view.expenses

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.databinding.FragmentEnterIncomeBinding
import com.artyomefimov.expensescontrol.presentation.ext.formatToAmount
import com.artyomefimov.expensescontrol.presentation.ext.fractionFormatter
import com.artyomefimov.expensescontrol.presentation.ext.observeEvent
import com.artyomefimov.expensescontrol.presentation.view.edittext.MoneyTextWatcher
import com.artyomefimov.expensescontrol.presentation.viewmodel.expenses.EnterIncomeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Экран, на котором происходит ввод суммы, доступной пользователю на месяц
 */
@AndroidEntryPoint
class EnterIncomeFragment : Fragment() {

    private lateinit var binding: FragmentEnterIncomeBinding
    private val viewModel: EnterIncomeViewModel by viewModels()
    private lateinit var textWatcher: MoneyTextWatcher

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
            viewModel.addIncome(
                stringSum = binding.enterMoneyEditText.text?.toString()?.formatToAmount()
            )
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.enterMoneyEditText.apply {
            textWatcher = MoneyTextWatcher(this, fractionFormatter)
            addTextChangedListener(textWatcher)
        }
        viewModel.navigateToExpenseScreen().observeEvent(this) {
            findNavController().navigate(R.id.action_enterIncomeFragment_to_expensesFragment)
        }
    }
}
