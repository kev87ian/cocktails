package com.kev.cocktailsdb.model


import com.google.gson.annotations.SerializedName

data class CocktailsResponse(
    @SerializedName("drinks")
    val drinks: List<Drink>
)