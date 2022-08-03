package com.kev.cocktailsdb.repository

import com.kev.cocktailsdb.api.ApiObject

class MainRepository {
    suspend fun getCocktails() = ApiObject.getClient().getAlcoholicCocktails()
     suspend fun getNAlcoholCocktails() = ApiObject.getClient().getNAlcoholicCocktails()
}
