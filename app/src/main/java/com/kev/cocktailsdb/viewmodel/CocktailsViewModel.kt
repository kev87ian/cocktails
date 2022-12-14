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
import com.kev.cocktailsdb.data.model.CocktailsResponse
import com.kev.cocktailsdb.data.repository.CocktailsRepository
import com.kev.cocktailsdb.util.Resource
import kotlinx.coroutines.launch
import java.io.IOException


class CocktailsViewModel constructor(
    app: HiltApplication,
    private val repository: CocktailsRepository
) :
    AndroidViewModel(app) {


    private val _downloadedAlcoholResponse = MutableLiveData<Resource<CocktailsResponse>>()
    val downloadedAlcoholResponse: LiveData<Resource<CocktailsResponse>>
        get() = _downloadedAlcoholResponse


    private val _nDownloadedAlcoholResponse = MutableLiveData<Resource<CocktailsResponse>>()
    val nDownloadedAlcoholResponse: LiveData<Resource<CocktailsResponse>>
        get() = _nDownloadedAlcoholResponse


    private suspend fun safeAlcoholCall() = viewModelScope.launch {
        _downloadedAlcoholResponse.postValue(Resource.Loading())

        try {
            if (hasInternet()) {
                val response = repository.getCocktails()
                _downloadedAlcoholResponse.postValue(Resource.Loading())
                response.body()?.let {
                    _downloadedAlcoholResponse.postValue(Resource.Success(it))
                }
            }
            else {
                _downloadedAlcoholResponse.postValue(Resource.Error("No Internet Connection"))
            }
        }

        catch (t: Throwable) {
            when (t) {
                is IOException -> _downloadedAlcoholResponse.postValue(Resource.Error("No Internet Connection"))

                else -> _downloadedAlcoholResponse.postValue(Resource.Error("Json Conversion Error"))
            }
        }
    }

    private suspend fun safeNonAlcoholicCall() = viewModelScope.launch {
        _nDownloadedAlcoholResponse.postValue(Resource.Loading())

        try {
            if (hasInternet()) {
                val response = repository.getNAlcoholCocktails()
                response.body()?.let {
                    _nDownloadedAlcoholResponse.postValue(Resource.Success(it))
                }

            } else {
                _nDownloadedAlcoholResponse.postValue(Resource.Error("No internet connection."))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> _nDownloadedAlcoholResponse.postValue(Resource.Error("No internet connection."))
                else -> _nDownloadedAlcoholResponse.postValue(Resource.Error("Conversion Error."))
            }
        }
    }

     fun getAlcoholicCocktails() = viewModelScope.launch {
        safeAlcoholCall()
    }

     fun getNAlcoholicCocktails() = viewModelScope.launch {
        safeNonAlcoholicCall()
    }


    init {
        getAlcoholicCocktails()
        getNAlcoholicCocktails()
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