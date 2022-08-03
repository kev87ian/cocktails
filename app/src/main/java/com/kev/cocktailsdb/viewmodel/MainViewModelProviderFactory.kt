package com.kev.cocktailsdb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.repository.MainRepository

class MainViewModelProviderFactory(val app: HiltApplication,private val repository: MainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(app, repository) as T
    }
}