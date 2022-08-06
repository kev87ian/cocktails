package com.kev.cocktailsdb.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.ui.MainActivity
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModel

class CocktailDetailsFragment : Fragment(R.layout.fragment_cocktail_details){
private lateinit var detailsViewModel: CocktailDetailsViewModel

    val args: CocktailDetailsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsViewModel = (activity as MainActivity).detailsViewModel

        val drink = args.drink

        detailsViewModel.downloadedCocktailDetails.observe(viewLifecycleOwner, Observer {response->

        })
    }
}