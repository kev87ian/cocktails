package com.kev.cocktailsdb.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import androidx.lifecycle.*
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.db.AppDatabase
import com.kev.cocktailsdb.model.CocktailsResponse
import com.kev.cocktailsdb.model.Drink
import com.kev.cocktailsdb.repository.MainRepository
import com.kev.cocktailsdb.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class MainViewModel constructor(
    private val repository: MainRepository,

) : ViewModel() {

    //live data initialization
    private val _downloadedAlcoholResponse: MutableLiveData<Resource<CocktailsResponse>> =
        MutableLiveData()
    val downloadedAlcoholResponse: LiveData<Resource<CocktailsResponse>>
        get() = _downloadedAlcoholResponse


    private val _downloadedNAlcoholResponse: MutableLiveData<Resource<CocktailsResponse>> =
        MutableLiveData()
    val downloadedNanAlcoholResponse: LiveData<Resource<CocktailsResponse>>
        get() = _downloadedNAlcoholResponse


    init {
        getNAlcoholicCocktails()
        getAlcoholicCocktails()
    }


    private fun getAlcoholicCocktails() = viewModelScope.launch {
        _downloadedAlcoholResponse.postValue(Resource.Loading())
        val response = repository.getCocktails()
        _downloadedAlcoholResponse.postValue(handleAlcoholCocktails(response))
    }

    private fun handleAlcoholCocktails(response: Response<CocktailsResponse>): Resource<CocktailsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { cocktailResponse ->
                //               upsertRecords(cocktailResponse.drinks)
                return Resource.Success(cocktailResponse)
            }
        }

        return Resource.Error(response.message().toString())

    }

    private fun getNAlcoholicCocktails() = viewModelScope.launch {
        _downloadedNAlcoholResponse.postValue(Resource.Loading())
        val response = repository.getNAlcoholCocktails()
        _downloadedNAlcoholResponse.postValue(handleNAlcoholCocktails(response))
    }

    private fun handleNAlcoholCocktails(response: Response<CocktailsResponse>): Resource<CocktailsResponse> {
        if (response.isSuccessful) {

            response.body()?.let { cocktailsResponse ->
                //       upsertRecords(cocktailsResponse)
                return Resource.Success(cocktailsResponse)

            }
        }

        return Resource.Error(response.message().toString())
    }



}