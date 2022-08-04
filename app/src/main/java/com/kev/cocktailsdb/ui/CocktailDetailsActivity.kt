package com.kev.cocktailsdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.api.ApiObject
import com.kev.cocktailsdb.api.ApiService
import com.kev.cocktailsdb.model.Drink
import com.kev.cocktailsdb.repository.CocktailDetailsRepository
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModel
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModelProviderFactory
import com.kev.cocktailsdb.viewmodel.MainViewModel
import com.kev.cocktailsdb.viewmodel.MainViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_cocktail_details.*

class CocktailDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: CocktailDetailsViewModel
    private lateinit var repository: CocktailDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_details)

        val cocktailId: Int = intent.getIntExtra("id", 1)

        repository = CocktailDetailsRepository()

        val viewModelProviderFactory = CocktailDetailsViewModelProviderFactory(repository, cocktailId)
       viewModel = ViewModelProvider(this, viewModelProviderFactory)[CocktailDetailsViewModel::class.java]

        viewModel.downloadedCocktailDetails.observe(this){
            bindUI(it)
        }

    }



    private fun bindUI(it: Drink) {
        tvDetails_cocktail_name.text = it.strDrink
        Glide.with(baseContext).load(it.strDrinkThumb).into(ivDetails_cocktail_image)
    }


}


