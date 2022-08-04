package com.kev.cocktailsdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drinks")
data class Drink(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "idDrink") val idDrink: String,
    @ColumnInfo(name = "strAlcoholic") val strAlcoholic: String?,
    @ColumnInfo(name = "strCategory") val strCategory: String?,
    @ColumnInfo(name = "strDrink") val strDrink: String?,
    @ColumnInfo(name = "strDrinkThumb") val strDrinkThumb: String?,
    @ColumnInfo(name = "strGlass") val strGlass: String?
)