package com.kev.cocktailsdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.repository.CocktailDetailsRepository
import com.kev.cocktailsdb.repository.CocktailsRepository
import com.kev.cocktailsdb.ui.fragments.CocktailDetailsFragmentArgs
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModel
import com.kev.cocktailsdb.viewmodel.CocktailDetailsViewModelProviderFactory
import com.kev.cocktailsdb.viewmodel.CocktailsViewModel
import com.kev.cocktailsdb.viewmodel.MainViewModelProviderFactory


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: CocktailsViewModel
    lateinit var detailsViewModel: CocktailDetailsViewModel
    private lateinit var navController: NavController

    private val args : CocktailDetailsFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val cocktailsRepository = CocktailsRepository()
        val cocktailDetailsRepository = CocktailDetailsRepository()

        val detailsViewModelProviderFactory = CocktailDetailsViewModelProviderFactory(application as HiltApplication, cocktailDetailsRepository, cocktailId = 11786)

        detailsViewModel = ViewModelProvider(this, detailsViewModelProviderFactory)[CocktailDetailsViewModel::class.java]


        val viewModelProviderFactory = MainViewModelProviderFactory(application as HiltApplication, cocktailsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[CocktailsViewModel::class.java]

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.cocktailsNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
    }
}