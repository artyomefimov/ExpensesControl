package com.artyomefimov.expensescontrol.domain.interactor.category

import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.presentation.resources.ResourcesProvider
import javax.inject.Inject

class CategoryInteractorImpl @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
): CategoryInteractor {

    override fun getCategories(): List<String> {
        return listOf(
            resourcesProvider.getString(R.string.category_supermarkets),
            resourcesProvider.getString(R.string.category_fun),
            resourcesProvider.getString(R.string.category_transfers),
            resourcesProvider.getString(R.string.category_fast_food),
            resourcesProvider.getString(R.string.category_transport),
            resourcesProvider.getString(R.string.category_house),
            resourcesProvider.getString(R.string.category_clothes),
            resourcesProvider.getString(R.string.category_sports),
            resourcesProvider.getString(R.string.category_mandatory_payments),
            resourcesProvider.getString(R.string.category_other),
        )
    }
}
