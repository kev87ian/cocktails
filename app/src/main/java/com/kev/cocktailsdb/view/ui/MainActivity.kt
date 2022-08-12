package com.kev.cocktailsdb.view.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.data.repository.CocktailsRepository
import com.kev.cocktailsdb.view.viewmodelprovider.MainViewModelProviderFactory
import com.kev.cocktailsdb.viewmodel.CocktailsViewModel


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

            R.id.menuSearchQuery ->{
                searchCocktail()
            }
        }
        return true
    }
   private fun searchCocktail() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Cocktail Search.")
        //set up the input dialog
        val userInput = EditText(this)
        //specifying type of input
        userInput.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        userInput.hint = "Enter a cocktail name."
        builder.setView(userInput)


//setting up the alert dialogs buttons
        builder.setPositiveButton("Submit") { dialog, which ->
            //we getting user input
            val userQuery = userInput.text.toString().trim().lowercase()
            //checking if user input contains numbers
            if (userQuery.isEmpty() || !userQuery.matches("^[a-zA-Z ]*\$".toRegex())) { Toast.makeText(this, "Please ensure you have entered a valid cocktail name.", Toast.LENGTH_SHORT).show()

            } else {
                val intent = Intent(this, CocktailSearchActivity::class.java)
                //the intent will be the search query
                intent.putExtra("userQuery", userQuery)
                startActivity(intent)
            }
        }
       builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
        builder.show()
    }

    private fun randomCocktail(){
        intent = Intent(this, RandomCocktailActivity::class.java)
        startActivity(intent)
    }
}