package com.kev.cocktailsdb.view.viewmodelprovider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.data.repository.CocktailDetailsRepository
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModel

@Suppress("UNCHECKED_CAST")
class CocktailDetailsViewModelProviderFactory
    (private val app:HiltApplication,private val repository: CocktailDetailsRepository, private val cocktailId : Int) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CocktailDetailsViewModel(app,cocktailId, repository) as T
    }
}
