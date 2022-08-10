package com.kev.cocktailsdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.data.repository.RandomCocktailRepository

@Suppress("UNCHECKED_CAST")
class RandomCocktailViewModelProviderFactory(private val application: HiltApplication, private val repository: RandomCocktailRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RandomCocktailViewModel(application, repository) as T
    }
}