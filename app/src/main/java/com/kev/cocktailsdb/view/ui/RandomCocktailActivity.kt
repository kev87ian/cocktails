package com.kev.cocktailsdb.view.ui

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.data.model.Drink
import com.kev.cocktailsdb.data.repository.RandomCocktailRepository
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.viewmodel.RandomCocktailViewModel
import com.kev.cocktailsdb.viewmodel.RandomCocktailViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_random_cocktail.*


class RandomCocktailActivity : AppCompatActivity() {
    lateinit var viewModel: RandomCocktailViewModel
    lateinit var repository: RandomCocktailRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_cocktail)






        repository = RandomCocktailRepository()

        val viewModelProviderFactory =
            RandomCocktailViewModelProviderFactory(application as HiltApplication, repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[RandomCocktailViewModel::class.java]

        viewModel.randomCocktail.observe(this, Observer { response ->

            when (response) {
                is Resource.Error -> {


                }

                is Resource.Success -> {
                    response.data?.let {

                        val drink = it.drinks[0]
                        setUI(drink)
                    }

                }

                is Resource.Loading -> {

                }
            }
        })
    }

    private fun setUI(drink: Drink) {

        Glide.with(baseContext).load(drink.strDrinkThumb).placeholder(R.drawable.loading)
            .into(randomCocktailImageView)
        randomCocktailNameTv.text = drink.strDrink
        Toast.makeText(this, drink.strDrink, Toast.LENGTH_LONG).show()


        val ingredientList = arrayOf(
            drink.strIngredient1,
            drink.strIngredient2,
            drink.strIngredient3,
            drink.strIngredient4,
            drink.strIngredient5
        )

        val measurementLis: Array<String> = arrayOf(
            drink.strMeasure1,
            drink.strMeasure2,
            drink.strMeasure3,
            drink.strMeasure4,
            drink.strMeasure5
        )


        for (i in ingredientList.size..25) {
            val dynamicTextview = TextView(this)
           tvMeasurements.text = ingredientList[0]

        }
    }


}