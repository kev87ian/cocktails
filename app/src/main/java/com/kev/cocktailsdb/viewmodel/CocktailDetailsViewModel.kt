package com.kev.cocktailsdb.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.cocktailsdb.model.Drink
import com.kev.cocktailsdb.repository.CocktailDetailsRepository
import kotlinx.coroutines.launch

class CocktailDetailsViewModel(
    cocktailId: Int, private val repository: CocktailDetailsRepository
) :
    ViewModel() {

    private val _downloadedCocktailDetails = MutableLiveData<Drink>()
    val downloadedCocktailDetails: LiveData<Drink>
        get() = _downloadedCocktailDetails


    private suspend fun safeDetailsCall(cocktailId: Int) = viewModelScope.launch {
        val response = repository.getCocktailDetails(cocktailId)
        _downloadedCocktailDetails.postValue(response.body())
    }


    private fun getCocktailDetails(cocktailId: Int) = viewModelScope.launch {
        safeDetailsCall(cocktailId)

    }


    init {
        getCocktailDetails(cocktailId)
        Log.e("URL", getCocktailDetails(cocktailId).toString())
    }
}