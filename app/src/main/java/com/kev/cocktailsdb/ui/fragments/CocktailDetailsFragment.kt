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

