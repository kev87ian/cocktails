package com.kev.cocktailsdb.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.data.model.CocktailsResponse
import com.kev.cocktailsdb.data.repository.SearchCocktailsRepository
import com.kev.cocktailsdb.util.Resource
import kotlinx.coroutines.launch

class CocktailSearchViewModel(
    application: HiltApplication, cocktailName: String,
    private val repository: SearchCocktailsRepository
) : AndroidViewModel(application) {

    val cocktailSearchResponse: MutableLiveData<Resource<CocktailsResponse>> =
        MutableLiveData()


    private suspend fun safeSearchQuery(cocktailName: String) = viewModelScope.launch {
        cocktailSearchResponse.postValue(Resource.Loading())
        val response = repository.searchCocktails(cocktailName)

        try {

            if (hasInternet()&&!response.body()?.drinks.isNullOrEmpty()){
                response.body()?.let {
                    cocktailSearchResponse.postValue(Resource.Success(it))
                }
            }
            else if(!hasInternet()){
                cocktailSearchResponse.postValue(Resource.Error("Ensure you have an active internet connection."))
            }

            else if(hasInternet() && response.body()?.drinks.isNullOrEmpty()){
                cocktailSearchResponse.postValue(Resource.Error("No cocktails found."))
            }


        } catch (e: Exception) {

        }
    }


    private  fun searchCocktails(cocktailName: String) = viewModelScope.launch {
        safeSearchQuery(cocktailName)
    }


    init {
        searchCocktails(cocktailName)
    }

    private fun hasInternet(): Boolean {
        val connectivityManager = getApplication<HiltApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> return true
                    ConnectivityManager.TYPE_MOBILE -> return true
                    ConnectivityManager.TYPE_ETHERNET -> return true


                    else -> false
                }
            }
        }

        return false
    }
}