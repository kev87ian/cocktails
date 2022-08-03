package com.kev.cocktailsdb.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.model.CocktailsResponse
import com.kev.cocktailsdb.repository.MainRepository
import com.kev.cocktailsdb.util.Resource
import kotlinx.coroutines.launch
import java.io.IOException


class MainViewModel constructor(app: HiltApplication, private val repository: MainRepository) :
    AndroidViewModel(app) {


    private val _downloadedAlcoholResponse = MutableLiveData<Resource<CocktailsResponse>>()
    val downloadedAlcoholResponse: LiveData<Resource<CocktailsResponse>>
        get() = _downloadedAlcoholResponse


    private suspend fun safeAlcoholCall() = viewModelScope.launch {
        _downloadedAlcoholResponse.postValue(Resource.Loading())
        try {
            if (hasInternet()) {
                val response = repository.getCocktails()

                response.body()?.let {
                    _downloadedAlcoholResponse.postValue(Resource.Success(it))
                }
            } else {
                _downloadedAlcoholResponse.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _downloadedAlcoholResponse.postValue(Resource.Error("Network Failure"))

                else -> _downloadedAlcoholResponse.postValue(Resource.Error("Json Conversion Error"))
            }
        }
    }


    private fun getAlcoholicCocktails() = viewModelScope.launch {
        safeAlcoholCall()
    }

    init {
        getAlcoholicCocktails()
    }

/*
    private fun getNalcoholicCocktails() = viewModelScope.launch {

        val response = repository.getNAlcoholCocktails()
        if (response.isSuccessful) {
            response.body().let {
                _downloadedAlcoholResponse.postValue(it)

            }
        } else {
            Log.e("Makosa", response.message().toString())


        }
    }*/

    private fun hasInternet(): Boolean {
        val connectivityManager = getApplication<HiltApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true

                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> return true
                    TYPE_MOBILE -> return true
                    TYPE_ETHERNET -> return true


                    else -> false
                }
            }
        }

        return false
    }

}