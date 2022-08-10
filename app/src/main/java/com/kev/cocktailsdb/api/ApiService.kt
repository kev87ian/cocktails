package com.kev.cocktailsdb.api

import com.kev.cocktailsdb.model.CocktailsResponse
import com.kev.cocktailsdb.model.Drink
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("1/filter.php?a=Alcoholic")
    ///filter.php?a=Alcoholic
    suspend fun getAlcoholicCocktails(): Response<CocktailsResponse>

    @GET("1/filter.php?a=Non_Alcoholic")
    suspend fun getNAlcoholicCocktails():Response<CocktailsResponse>

    //https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=11007
    @GET("1/lookup.php?")
    suspend fun getCocktailDetails(@Query("i")id : Int) : Response<CocktailsResponse>

    @GET("1/random.php")
    suspend fun getRandomCocktail() : Response<CocktailsResponse>
}