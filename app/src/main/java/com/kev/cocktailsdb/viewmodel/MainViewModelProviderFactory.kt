package com.kev.cocktailsdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.data.repository.CocktailsRepository

class MainViewModelProviderFactory(val app: HiltApplication,private val repository: CocktailsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CocktailsViewModel(app, repository) as T
    }
}