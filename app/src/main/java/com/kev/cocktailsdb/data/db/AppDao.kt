package com.kev.cocktailsdb.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kev.cocktailsdb.data.model.Drink

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRecords(drink: Drink):Long

    /* The function below is not a suspend because it returns livedata*/
    @Query("SELECT * FROM drinks")
    fun getAllCocktails(): LiveData<List<Drink>>

    @Delete
    suspend fun deleteRecords(drink: Drink)
}
