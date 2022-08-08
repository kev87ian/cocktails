package com.kev.cocktailsdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.api.ApiObject
import com.kev.cocktailsdb.api.ApiService
import com.kev.cocktailsdb.model.CocktailsResponse
import com.kev.cocktailsdb.repository.CocktailDetailsRepository
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModel
import kotlinx.android.synthetic.main.activity_cocktails_details.*
import kotlinx.android.synthetic.main.fragment_cocktail_details.*
import org.json.JSONObject

class CocktailsDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: CocktailDetailsViewModel
    private lateinit var repository : CocktailDetailsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails_details)


        val cocktailId : Int = intent.getIntExtra("id", 1)
        repository = CocktailDetailsRepository()
        viewModel = getViewModel(cocktailId)
        Toast.makeText(this, "cocktail ID is $cocktailId", Toast.LENGTH_LONG).show()


        viewModel.downloadedCocktailDetails.observe(this, Observer{response->

            when(response){
                is Resource.Loading ->{
                 //   pb.visibility = View.VISIBLE
                }
                is Resource.Error ->{
                  /*  errorTVDETAILS.visibility = View.VISIBLE
                    errorTVDETAILS.text = response.message
                    pb.visibility = View.GONE*/
                }

                is Resource.Success ->{
                  val data = response.data?.drinks.toString()

                    try {

                        val jObject = JSONObject(data)
                        val jArray = jObject.getJSONArray("drinks")
                        Log.i("meals", jArray.toString())
                        for (i in 0 until  jArray.length()){
                            val cocktail = jArray.getJSONObject(i)
                            val imageUrl = cocktail.getString("strDrinkThumb")
                           val cocktailIngredient = cocktail.getString("strIngredient1")
                            ingredient1.text = cocktailIngredient
                        }


                    } catch (e:Exception){
                        e.printStackTrace()
                        Toast.makeText(this, "$e", Toast.LENGTH_LONG).show()

                    }
                }
            }
        })

    }



    private fun getViewModel(cocktailId: Int): CocktailDetailsViewModel {

        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CocktailDetailsViewModel(application as HiltApplication, cocktailId, repository) as T
            }

        })[CocktailDetailsViewModel::class.java]

    }


    private fun loadUrl(){

    }
}