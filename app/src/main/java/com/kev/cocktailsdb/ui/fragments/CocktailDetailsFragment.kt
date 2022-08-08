package com.kev.cocktailsdb.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.databinding.FragmentCocktailDetailsBinding
import com.kev.cocktailsdb.model.Drink
import com.kev.cocktailsdb.ui.MainActivity
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModel
import com.kev.cocktailsdb.viewmodel.CocktailsViewModel
import kotlinx.android.synthetic.main.fragment_cocktail_details.*

class CocktailDetailsFragment : Fragment(R.layout.fragment_cocktail_details){
    private lateinit var viewModel: CocktailDetailsViewModel
    private lateinit var drink : Drink


    private val args :CocktailDetailsFragmentArgs by navArgs()



//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

     super.onViewCreated(view, savedInstanceState)
        drink = args.drink
        viewModel = (activity as MainActivity).detailsViewModel
        populateUI()
    }

    private fun populateUI() {
        viewModel.downloadedCocktailDetails.observe(viewLifecycleOwner, Observer {response->


        })
     }

}
