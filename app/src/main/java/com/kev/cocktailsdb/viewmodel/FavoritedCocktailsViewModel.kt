package com.kev.cocktailsdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.cocktailsdb.data.model.Drink
import com.kev.cocktailsdb.data.repository.FavoritedCocktailsRepository
import kotlinx.coroutines.launch

class FavoritedCocktailsViewModel(val repository: FavoritedCocktailsRepository) : ViewModel(){

    fun getSavedCocktails() = repository.getSavedCocktails()

    fun deleteCocktail(drink: Drink) = viewModelScope.launch {
        repository.deleteCocktail(drink)
    }
}