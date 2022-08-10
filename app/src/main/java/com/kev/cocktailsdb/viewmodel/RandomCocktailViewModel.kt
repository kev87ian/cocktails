package com.kev.cocktailsdb.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.data.model.CocktailsResponse
import com.kev.cocktailsdb.data.repository.RandomCocktailRepository
import com.kev.cocktailsdb.util.Resource
import kotlinx.coroutines.launch
import java.io.IOException

class RandomCocktailViewModel(
    application: HiltApplication,
    val repository: RandomCocktailRepository
) : AndroidViewModel(application) {

    private val _randomCocktail = MutableLiveData<Resource<CocktailsResponse>>()
    val randomCocktail: LiveData<Resource<CocktailsResponse>>
        get() = _randomCocktail


    private suspend fun safeRandomCocktailCall() = viewModelScope.launch {
        _randomCocktail.postValue(Resource.Loading())
        try {
            if (hasInternet()) {

                val response = repository.getRandomCocktail()
                response.body()?.let {
                    _randomCocktail.postValue(Resource.Success(it))
                }

            } else {
                _randomCocktail.postValue(Resource.Error("No internet connection."))
            }

        } catch (e: Exception) {
            when (e) {
                is IOException -> _randomCocktail.postValue(Resource.Error("No internet connection."))

                else -> _randomCocktail.postValue(Resource.Error("JSON conversion error."))
            }
        }

    }

    private fun getRandomCocktail() = viewModelScope.launch {
        safeRandomCocktailCall()
    }

    init {
        getRandomCocktail()
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