package com.kev.cocktailsdb.view.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.data.db.AppDatabase
import com.kev.cocktailsdb.data.model.Drink
import com.kev.cocktailsdb.data.repository.RandomCocktailRepository
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.view.viewmodelprovider.RandomCocktailViewModelProviderFactory
import com.kev.cocktailsdb.viewmodel.RandomCocktailViewModel
import kotlinx.android.synthetic.main.activity_random_cocktail.*


class RandomCocktailActivity : AppCompatActivity() {
    lateinit var viewModel: RandomCocktailViewModel
    lateinit var repository: RandomCocktailRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_cocktail)


        val db = AppDatabase.invoke(baseContext)
        repository = RandomCocktailRepository(db)

        val viewModelProviderFactory =
            RandomCocktailViewModelProviderFactory(application as HiltApplication, repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[RandomCocktailViewModel::class.java]

        viewModel.randomCocktail.observe(this) { response ->

            when (response) {
                is Resource.Error -> {

                    hideViews()
                }

                is Resource.Success -> {
                    showViews()
                    response.data?.let {

                        val drink = it.drinks[0]
                        setUI(drink)
                        addTofavorites(drink)
                    }

                }

                is Resource.Loading -> {
                  onError()
                    errorRandomCocktail.text = response.message
                    randomCocktailsProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun hideViews(){
        /*Loading*/
        randomCocktailImageView.visibility = View.GONE
        randomCocktailsProgressBar.visibility = View.VISIBLE
        randomCocktailInstructionsTv.visibility = View.GONE
        cardView8.visibility = View.GONE
        drinkDetailIngredient1Tv.visibility = View.GONE
        drinkDetailMeasurement1Tv.visibility = View.GONE
        addToFavorites.visibility = View.GONE
        cardview1.visibility = View.GONE
        tableeee.visibility = View.GONE
        tableMeasarement.visibility = View.GONE
        instrudctionsHeader.visibility = View.GONE
    }

    private fun showViews(){
/*success*/
        randomCocktailImageView.visibility = View.VISIBLE
        randomCocktailDetailsCategoryTV.visibility = View.VISIBLE
        randomCocktailInstructionsTv.visibility = View.VISIBLE
        randomCocktailGlassTv.visibility = View.VISIBLE
        drinkDetailIngredient1Tv.visibility = View.VISIBLE
        drinkDetailMeasurement1Tv.visibility = View.VISIBLE
        addToFavorites.visibility = View.VISIBLE
        cardview1.visibility = View.VISIBLE
        cardView8.visibility = View.VISIBLE
        randomCocktailsProgressBar.visibility = View.GONE
        tableMeasarement.visibility = View.VISIBLE
        tableeee.visibility = View.VISIBLE
        randomCocktailsProgressBar.visibility = View.GONE
        instrudctionsHeader.visibility = View.VISIBLE
        /*       toolbar.visibility = View.VISIBLE*/
    }

    private fun onError(){
        hideViews()
        randomCocktailsProgressBar.visibility = View.GONE
        errorRandomCocktail.visibility = View.VISIBLE
    }



    private fun addTofavorites(drink: Drink) {

        addToFavorites.setOnClickListener {
            viewModel.saveCocktail(drink)
            Toast.makeText(baseContext, "Cocktail favorited!", Toast.LENGTH_LONG).show()
        }
    }

    private fun setUI(drink: Drink) {

        Glide.with(baseContext).load(drink.strDrinkThumb).placeholder(R.drawable.loading)
            .into(randomCocktailImageView)
        cocktailName.text = drink.strDrink

        randomCocktailDetailsCategoryTV.text = drink.strAlcoholic
        randomCocktailGlassTv.text = drink.strGlass
        randomCocktailInstructionsTv.text = drink.strInstructions


        if (!drink.strIngredient1.isNullOrEmpty()) {
            drinkDetailIngredient1Tv.append("\n \u2022 ".plus(drink.strIngredient1))
        }


        if (!drink.strIngredient2.isNullOrEmpty()) {
            drinkDetailIngredient1Tv.append("\n \u2022 ".plus(drink.strIngredient2))
        }

        if (!drink.strIngredient3.isNullOrEmpty()) {
            drinkDetailIngredient1Tv.append("\n \u2022 ".plus(drink.strIngredient3))
        }

        if (!drink.strIngredient4.isNullOrEmpty()) {
            drinkDetailIngredient1Tv.append("\n \u2022 ".plus(drink.strIngredient4))
        }

        if (!drink.strIngredient5.isNullOrEmpty()) {
            drinkDetailIngredient1Tv.append("\n \u2022 ".plus(drink.strIngredient5))
        }
        if (!drink.strIngredient6.isNullOrEmpty()) {
            drinkDetailIngredient1Tv.append("\n \u2022 ".plus(drink.strIngredient6))
        }
        if (!drink.strIngredient7.isNullOrEmpty()) {
            drinkDetailIngredient1Tv.append("\n \u2022 ".plus(drink.strIngredient7))
        }
        if (!drink.strIngredient8.isNullOrEmpty()) {
            drinkDetailIngredient1Tv.append("\n \u2022 ".plus(drink.strIngredient8))
        }
        if (!drink.strIngredient9.isNullOrEmpty()) {
            drinkDetailIngredient1Tv.append("\n \u2022 ".plus(drink.strIngredient9))
        }
        if (!drink.strIngredient10.isNullOrEmpty()) {
            drinkDetailIngredient1Tv.append("\n \u2022 ".plus(drink.strIngredient10))
        }
        if (!drink.strIngredient11.isNullOrEmpty()) {
            drinkDetailIngredient1Tv.append("\n \u2022 ".plus(drink.strIngredient11))
        }





        if (!drink.strMeasure1.isNullOrEmpty()) {
            drinkDetailMeasurement1Tv.append("\n : ".plus(drink.strMeasure1))
        }

        if (!drink.strMeasure2.isNullOrEmpty()) {
            drinkDetailMeasurement1Tv.append("\n : ".plus(drink.strMeasure2))
        }

        if (!drink.strMeasure3.isNullOrEmpty()) {
            drinkDetailMeasurement1Tv.append("\n : ".plus(drink.strMeasure3))
        }

        if (!drink.strMeasure4.isNullOrEmpty()) {
            drinkDetailMeasurement1Tv.append("\n : ".plus(drink.strMeasure4))
        }

        if (!drink.strMeasure5.isNullOrEmpty()) {
            drinkDetailMeasurement1Tv.append("\n : ".plus(drink.strMeasure5))
        }


        if (!drink.strMeasure6.isNullOrEmpty()) {
            drinkDetailMeasurement1Tv.append("\n : ".plus(drink.strMeasure6))
        }


        if (!drink.strMeasure7.isNullOrEmpty()) {
            drinkDetailMeasurement1Tv.append("\n : ".plus(drink.strMeasure7))
        }


        if (!drink.strMeasure8.isNullOrEmpty()) {
            drinkDetailMeasurement1Tv.append("\n : ".plus(drink.strMeasure8))
        }


        if (!drink.strMeasure9.isNullOrEmpty()) {
            drinkDetailMeasurement1Tv.append("\n : ".plus(drink.strMeasure9))
        }


        if (!drink.strMeasure10.isNullOrEmpty()) {
            drinkDetailMeasurement1Tv.append("\n : ".plus(drink.strMeasure10))
        }


        if (!drink.strMeasure11.isNullOrEmpty()) {
            drinkDetailMeasurement1Tv.append("\n : ".plus(drink.strMeasure11))
        }


    }
}