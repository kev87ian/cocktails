package com.kev.cocktailsdb.ui


import android.R.attr
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.model.CocktailsResponse
import com.kev.cocktailsdb.model.Drink
import com.kev.cocktailsdb.repository.CocktailDetailsRepository
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModel
import kotlinx.android.synthetic.main.activity_cocktails_details.*
import org.json.JSONObject
import java.lang.reflect.Type


class CocktailsDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: CocktailDetailsViewModel
    private lateinit var repository: CocktailDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails_details)

        val cocktailId: Int = intent.getIntExtra("id", 1)
        repository = CocktailDetailsRepository()
        viewModel = getViewModel(cocktailId)


        viewModel.downloadedCocktailDetails.observe(this, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    progress_bar.visibility = View.GONE
                    response.data.let {
                        val drink = it?.drinks?.get(0)
                        setCocktail(drink)
                    }

                }
                is Resource.Loading -> {
                    progress_bar.visibility = View.VISIBLE

                }

                is Resource.Error -> {
                    txt_error.visibility = View.VISIBLE
                    txt_error.text = response.message.toString()
                    progress_bar.visibility = View.GONE
                }

            }
        })

    }

    private fun setCocktail(drink: Drink?) {

        Glide.with(baseContext).load(drink?.strDrinkThumb).placeholder(R.drawable.loading)
            .into(cocktailDetailsImageView)
        cocktailDetailsNameTv.text = drink?.strDrink

        cocktailDetailsIngredientsTV.text =
            drink?.strIngredient1?.plus(",").plus(" ").plus(drink?.strIngredient2).plus(",")
                .plus(" ").plus(drink?.strIngredient3).plus(",").plus(" ")
                .plus(drink?.strIngredient4)
        cocktailDetailsInstructionsTV.text = drink?.strInstructions
        cocktailDetailsCategoryTV.text = drink?.strCategory
        cocktailDetailsAlcoholicTV.text = drink?.strAlcoholic
    }


    private fun getViewModel(cocktailId: Int): CocktailDetailsViewModel {

        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CocktailDetailsViewModel(
                    application as HiltApplication,
                    cocktailId,
                    repository
                ) as T
            }

        })[CocktailDetailsViewModel::class.java]

    }


}

