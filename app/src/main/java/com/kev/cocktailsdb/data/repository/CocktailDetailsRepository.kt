package com.kev.cocktailsdb.data.repository

import com.kev.cocktailsdb.data.api.ApiObject

class CocktailDetailsRepository {
    suspend fun getCocktailDetails(cocktailId:Int) = ApiObject.getClient().getCocktailDetails(cocktailId)
}