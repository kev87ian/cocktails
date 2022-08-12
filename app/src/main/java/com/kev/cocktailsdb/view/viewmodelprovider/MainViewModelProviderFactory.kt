package com.kev.cocktailsdb.view.viewmodelprovider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.data.repository.CocktailsRepository
import com.kev.cocktailsdb.viewmodel.CocktailsViewModel

class MainViewModelProviderFactory(val app: HiltApplication,private val repository: CocktailsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CocktailsViewModel(app, repository) as T
    }
}