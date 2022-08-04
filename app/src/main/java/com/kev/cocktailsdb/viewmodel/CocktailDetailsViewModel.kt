package com.kev.cocktailsdb.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import com.bumptech.glide.Glide.init
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.model.Drink
import com.kev.cocktailsdb.repository.CocktailDetailsRepository
import com.kev.cocktailsdb.util.Resource
import kotlinx.coroutines.launch
import java.io.IOException

class CocktailDetailsViewModel(app: HiltApplication,
    cocktailId: String, private val repository: CocktailDetailsRepository
) :
    AndroidViewModel(app) {

    private val _downloadedCocktailDetails = MutableLiveData<Resource<Drink>>()
    val downloadedCocktailDetails: LiveData<Resource<Drink>>
        get() = _downloadedCocktailDetails



    private suspend fun safeDetailsCall(cocktailId: String) = viewModelScope.launch {
        _downloadedCocktailDetails.postValue(Resource.Loading())
        try {
            if (hasInternet()){
                val response = repository.getCocktailDetails(cocktailId)
                response.body()?.let {
                    _downloadedCocktailDetails.postValue(Resource.Success(it))
                }
            }
            else{
                _downloadedCocktailDetails.postValue(Resource.Error("No internet Connection."))
            }

        } catch (t:Throwable){

            when (t) {
                is IOException ->_downloadedCocktailDetails.postValue(Resource.Error("Network Failure"))

                else -> _downloadedCocktailDetails.postValue(Resource.Error("Json Conversion Error"))
            }
        }
    }


    private fun getCocktailDetails(cocktailId: String) = viewModelScope.launch {
        safeDetailsCall(cocktailId)

    }


    init {
        getCocktailDetails(cocktailId)
        Log.e("URL", getCocktailDetails(cocktailId).toString())
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