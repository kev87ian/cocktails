package com.kev.cocktailsdb.data.repository

import com.kev.cocktailsdb.data.api.ApiObject
import com.kev.cocktailsdb.data.db.AppDatabase
import com.kev.cocktailsdb.data.model.Drink

class CocktailDetailsRepository (val db:AppDatabase){
    suspend fun getCocktailDetails(cocktailId:Int) = ApiObject.getClient().getCocktailDetails(cocktailId)

    suspend fun upsert(drink: Drink) = db.getDrinksDao().upsertRecords(drink)


}