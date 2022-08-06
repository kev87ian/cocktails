package com.kev.cocktailsdb.repository

import com.kev.cocktailsdb.api.ApiObject

class CocktailsRepository {
    suspend fun getCocktails() = ApiObject.getClient().getAlcoholicCocktails()
    suspend fun getNAlcoholCocktails() = ApiObject.getClient().getNAlcoholicCocktails()
}
