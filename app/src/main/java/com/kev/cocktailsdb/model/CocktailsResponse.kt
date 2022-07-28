package com.kev.cocktailsdb.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.security.acl.Owner

data class CocktailsResponse(
    val drinks: List<Drink>
)


class TypeConverterList {



    @TypeConverter
    fun listToJson(value: List<CocktailsResponse>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<CocktailsResponse>::class.java).toList()
}
