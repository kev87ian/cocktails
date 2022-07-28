package com.kev.cocktailsdb.api

import com.kev.cocktailsdb.model.CocktailsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("1/filter.php?a=Alcoholic")
    ///filter.php?a=Alcoholic
    suspend fun getAlcoholicCocktails(): Response<CocktailsResponse>

    @GET("1/filter.php?a=Non_Alcoholic")
    suspend fun getNAlcoholicCocktails():Response<CocktailsResponse>

}