package com.kev.cocktailsdb.data.repository

import com.kev.cocktailsdb.data.api.ApiObject

class SearchCocktailsRepository {
    suspend fun searchCocktails(name : String) = ApiObject.getClient().searchCocktail(name)
}