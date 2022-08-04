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
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_cocktail_details.*

class CocktailDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: CocktailDetailsViewModel
    private lateinit var repository: CocktailDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_details)

        val cocktailId = intent.getStringExtra("idDrink")
        Log.d("DEBUG", cocktailId.toString())
        Toast.makeText(this@CocktailDetailsActivity, "${intent.getStringExtra("idDrink")}", Toast.LENGTH_LONG).show()


        repository = CocktailDetailsRepository()

        val viewModelProviderFactory = CocktailDetailsViewModelProviderFactory(application as HiltApplication,repository, cocktailId.toString())
       viewModel = ViewModelProvider(this, viewModelProviderFactory)[CocktailDetailsViewModel::class.java]


        viewModel.downloadedCocktailDetails.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    tvDetails_cocktail_name.text = response.data?.strDrink
                    Toast.makeText(this@CocktailDetailsActivity, "${response.data?.strDrink} is the drink name", Toast.LENGTH_LONG).show()
                    Glide.with(this).load(response.data?.strDrinkThumb).fitCenter()
                        .into(ivDetails_cocktail_image)
                    pb.visibility = View.GONE
                }

                is Resource.Loading -> {
                    pb.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    pb.visibility = View.GONE
                    Toast.makeText(this, "${response.message}", Toast.LENGTH_LONG).show()
                }


            }
        })


}
}


