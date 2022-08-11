package com.kev.cocktailsdb.viewmodel

import androidx.lifecycle.AndroidViewModel
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.data.repository.SearchCocktailsRepository

class SearchCocktailsViewModel(private val application: HiltApplication, private val repository: SearchCocktailsRepository) : AndroidViewModel(application) {


}