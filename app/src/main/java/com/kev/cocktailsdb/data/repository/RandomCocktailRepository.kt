package com.kev.cocktailsdb.data.repository

import com.kev.cocktailsdb.data.api.ApiObject

class RandomCocktailRepository {

    suspend fun getRandomCocktail() = ApiObject.getClient().getRandomCocktail()
}