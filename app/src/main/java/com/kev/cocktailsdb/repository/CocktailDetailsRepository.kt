package com.kev.cocktailsdb.repository

import com.kev.cocktailsdb.api.ApiObject

class CocktailDetailsRepository {
    suspend fun getCocktailDetails(cocktailId:Int) = ApiObject.getClient().getCocktailDetails(cocktailId)

}