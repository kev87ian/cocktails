package com.kev.cocktailsdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.model.Drink
import com.kev.cocktailsdb.repository.CocktailDetailsRepository
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModel
import kotlinx.android.synthetic.main.activity_cocktails_details.*

class RandomCocktailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails_details)

    }
}