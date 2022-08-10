package com.kev.cocktailsdb.data.repository

import com.kev.cocktailsdb.data.api.ApiObject

class CocktailsRepository {
    suspend fun getCocktails() = ApiObject.getClient().getAlcoholicCocktails()
    suspend fun getNAlcoholCocktails() = ApiObject.getClient().getNAlcoholicCocktails()
}
