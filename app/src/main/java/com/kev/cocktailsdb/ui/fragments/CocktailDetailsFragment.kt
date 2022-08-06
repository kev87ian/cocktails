package com.kev.cocktailsdb.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.ui.MainActivity
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModel
import kotlinx.android.synthetic.main.fragment_cocktail_details.*

class CocktailDetailsFragment : Fragment(R.layout.fragment_cocktail_details){
private lateinit var detailsViewModel: CocktailDetailsViewModel

    private val args: CocktailDetailsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsViewModel = (activity as MainActivity).detailsViewModel

        val drink = args.drink

        detailsViewModel.downloadedCocktailDetails.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is  Resource.Loading ->{
                    pb.visibility = View.VISIBLE
                }
                is Resource.Error ->{
                    errorTVDETAILS.visibility = View.VISIBLE
                    errorTVDETAILS.text = response.message
                    pb.visibility = View.GONE
                }

                is Resource.Success ->{
                    tvDetails_cocktail_name.text = drink.strDrink
                    Glide.with(activity as MainActivity).load(drink.strDrinkThumb).placeholder(R.drawable.loading).into(ivDetails_cocktail_image)
//                    Glide.with(this).load(response.data?.strDrinkThumb).placeholder(R.drawable.loading).fitCenter().into(ivDetails_cocktail_image)
//                    pb.visibility =View.GONE
//                    Log.i("IDD", response.data?.strDrink.toString())
                }
            }
        })
    }
}