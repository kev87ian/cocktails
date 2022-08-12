
package com.kev.cocktailsdb.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kev.cocktailsdb.data.model.Drink

@Database(entities = [Drink::class], version = 1, exportSchema = false)

abstract class AppDatabase  : RoomDatabase() {

    abstract fun getAppDao(): AppDao

    companion object {
        private var DB_INSTANCE: AppDatabase? = null

        fun getDbInstance(context: Context): AppDatabase {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "APP_DB"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return DB_INSTANCE!!
        }
    }
}

