package com.artyomefimov.expensescontrol.presentation.view.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.databinding.FragmentEnterIncomeBinding
import com.artyomefimov.expensescontrol.presentation.ext.formatToAmount
import com.artyomefimov.expensescontrol.presentation.ext.fractionFormatter
import com.artyomefimov.expensescontrol.presentation.ext.observeEvent
import com.artyomefimov.expensescontrol.presentation.ext.showSnackbar
import com.artyomefimov.expensescontrol.presentation.view.edittext.MoneyTextWatcher
import com.artyomefimov.expensescontrol.presentation.viewmodel.expenses.EnterIncomeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Экран, на котором происходит ввод суммы, доступной пользователю на месяц
 */
@AndroidEntryPoint
class EnterIncomeFragment : Fragment() {

    private val viewModel: EnterIncomeViewModel by viewModels()
    private lateinit var binding: FragmentEnterIncomeBinding
    private lateinit var textWatcher: MoneyTextWatcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterIncomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.onIconPressedListener = {
            viewModel.addIncome(
                stringSum = binding.enterMoneyEditText.text?.toString()?.formatToAmount()
            )
        }
        binding.enterMoneyEditText.apply {
            textWatcher = MoneyTextWatcher(this, fractionFormatter)
            addTextChangedListener(textWatcher)
        }
        viewModel.navigateToExpenseScreen().observeEvent(this) {
            findNavController().navigate(R.id.action_enterIncomeFragment_to_expensesFragment)
        }
        viewModel.incorrectSum().observeEvent(this) {
            binding.root.showSnackbar(R.string.incorrect_sum)
        }
    }
}
