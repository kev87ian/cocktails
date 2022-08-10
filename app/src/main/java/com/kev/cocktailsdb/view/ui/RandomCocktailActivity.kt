package com.kev.cocktailsdb.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.data.model.Drink
import com.kev.cocktailsdb.data.repository.RandomCocktailRepository
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.viewmodel.CocktailsViewModel
import com.kev.cocktailsdb.viewmodel.MainViewModelProviderFactory
import com.kev.cocktailsdb.viewmodel.RandomCocktailViewModel
import com.kev.cocktailsdb.viewmodel.RandomCocktailViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_cocktails_details.*
import kotlinx.android.synthetic.main.activity_random_cocktail.*
import retrofit2.adapter.rxjava2.Result.response

class RandomCocktailActivity : AppCompatActivity() {
lateinit var viewModel: RandomCocktailViewModel
lateinit var repository: RandomCocktailRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_cocktail)

        repository = RandomCocktailRepository()

        val viewModelProviderFactory = RandomCocktailViewModelProviderFactory(application as HiltApplication, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[RandomCocktailViewModel::class.java]

        viewModel.randomCocktail.observe(this, Observer {response->

            when(response){
                is Resource.Error ->{


                }

                is Resource.Success ->{
                    response.data?.let {

                        val drink = it.drinks[0]
                        setUI(drink)
                    }

                }

                is Resource.Loading->{

                }
            }
        })
    }

    private fun setUI(drink: Drink) {

        Glide.with(baseContext).load(drink.strDrinkThumb).placeholder(R.drawable.loading).into(randomCocktailImageView)
        randomNameTV.text = drink.strDrink
        Toast.makeText(this, drink.strDrink, Toast.LENGTH_LONG).show()
    }
}