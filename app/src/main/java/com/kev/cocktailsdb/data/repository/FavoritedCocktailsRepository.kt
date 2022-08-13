package com.kev.cocktailsdb.data.repository

import com.kev.cocktailsdb.data.db.AppDatabase
import com.kev.cocktailsdb.data.model.Drink

class FavoritedCocktailsRepository(val db:AppDatabase) {
    fun getSavedCocktails() = db.getDrinksDao().getAllCocktails()

    suspend fun deleteCocktail(drink: Drink) = db.getDrinksDao().deleteRecords(drink)
}