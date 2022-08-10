package com.kev.cocktailsdb.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.repository.CocktailsRepository
import com.kev.cocktailsdb.viewmodel.CocktailsViewModel
import com.kev.cocktailsdb.viewmodel.MainViewModelProviderFactory


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: CocktailsViewModel

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val cocktailsRepository = CocktailsRepository()

        val viewModelProviderFactory = MainViewModelProviderFactory(application as HiltApplication, cocktailsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[CocktailsViewModel::class.java]

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.cocktailsNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_appbar, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuRandomCocktail ->{
                randomCocktail()
            }

        }
        return true
    }


    private fun randomCocktail(){
        intent = Intent(this, RandomCocktailActivity::class.java)
        startActivity(intent)
    }
}