package com.kev.cocktailsdb.data.repository

import com.kev.cocktailsdb.data.api.ApiObject
import com.kev.cocktailsdb.data.db.AppDatabase
import com.kev.cocktailsdb.data.model.Drink

class RandomCocktailRepository(val db:AppDatabase) {

    suspend fun getRandomCocktail() = ApiObject.getClient().getRandomCocktail()
    suspend fun upsert(drink: Drink) = db.getDrinksDao().upsertRecords(drink)

}