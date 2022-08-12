package com.kev.cocktailsdb.view.viewmodelprovider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.data.repository.SearchCocktailsRepository
import com.kev.cocktailsdb.viewmodel.CocktailSearchViewModel

@Suppress("UNCHECKED_CAST")
class CocktailSearchViewModelProviderFactory(
    private val repository: SearchCocktailsRepository,
    private val cocktailName: String,
    private val application: HiltApplication
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CocktailSearchViewModel(application, cocktailName, repository) as T
    }
}