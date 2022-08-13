package com.kev.cocktailsdb.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kev.cocktailsdb.data.model.Drink

@Database(entities = [Drink::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    //function that returns cocktail dao
    abstract fun getDrinksDao(): AppDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
/*This means that this cant be accessed by other threads at the same time */
            instance?: createDatabase(context).also{ instance = it}

        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "cocktails_db.db"
        ).build()
    }
}

