package com.kev.cocktailsdb.view.viewmodelprovider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kev.cocktailsdb.data.repository.FavoritedCocktailsRepository
import com.kev.cocktailsdb.viewmodel.FavoritedCocktailsViewModel

@Suppress("UNCHECKED_CAST")
class FavoritesViewModelProviderFactory(val repository: FavoritedCocktailsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoritedCocktailsViewModel(repository) as T
    }


}


