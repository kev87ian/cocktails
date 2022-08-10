/*
package com.kev.cocktailsdb.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kev.cocktailsdb.data.model.Drink

@Dao
interface AppDao {
    @Query("SELECT * FROM drinks")
    fun getAllRecords() : LiveData<List<Drink>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertRecords(drink: Drink)

    @Query("DELETE FROM drinks")
     fun deleteRecords()
}*/
