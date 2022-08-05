package com.kev.cocktailsdb.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.repository.CocktailDetailsRepository
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModel
import kotlinx.android.synthetic.main.activity_cocktail_details.*
import org.json.JSONObject

class CocktailDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: CocktailDetailsViewModel
    private lateinit var repository: CocktailDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_details)

        val cocktailId = intent.getIntExtra("idDrink", 1)
        Log.d("DEBUG", cocktailId.toString())
        Toast.makeText(this@CocktailDetailsActivity, "${intent.getIntExtra("idDrink", 1)}  is the id", Toast.LENGTH_LONG).show()

        repository = CocktailDetailsRepository()

//        val viewModelProviderFactory = CocktailDetailsViewModelProviderFactory(application as HiltApplication, repository, cocktailId)
//        viewModel = ViewModelProvider(this, viewModelProviderFactory)[CocktailDetailsViewModel::class.java]


        viewModel = getViewModel(cocktailId)

        viewModel.downloadedCocktailDetails.observe(this, Observer{response->

            when(response){
                is Resource.Loading ->{
                    pb.visibility = View.VISIBLE
                }
                is Resource.Error ->{
                    errorTVDETAILS.visibility = View.VISIBLE
                    errorTVDETAILS.text = response.message
                    pb.visibility = View.GONE
                }

                is Resource.Success ->{
                    val data  = response.data.toString()
                    tvDetails_cocktail_name.text = data.toString()
//                    Glide.with(this).load(response.data?.strDrinkThumb).placeholder(R.drawable.loading).fitCenter().into(ivDetails_cocktail_image)
//                    pb.visibility =View.GONE
//                    Log.i("IDD", response.data?.strDrink.toString())
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
    }



