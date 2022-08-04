package com.kev.cocktailsdb.repository

import androidx.lifecycle.LiveData
import com.kev.cocktailsdb.api.ApiObject
import com.kev.cocktailsdb.api.ApiService
import com.kev.cocktailsdb.model.Drink
import io.reactivex.disposables.CompositeDisposable

class CocktailDetailsRepository {
    suspend fun getCocktailDetails(cocktailId:String) = ApiObject.getClient().getCocktailDetails(cocktailId)
}