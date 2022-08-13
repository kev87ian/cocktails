package com.kev.cocktailsdb.view.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.data.db.AppDatabase
import com.kev.cocktailsdb.data.repository.FavoritedCocktailsRepository
import com.kev.cocktailsdb.view.adapter.FavoritedCocktailsAdapter
import com.kev.cocktailsdb.view.viewmodelprovider.FavoritesViewModelProviderFactory
import com.kev.cocktailsdb.viewmodel.FavoritedCocktailsViewModel
import kotlinx.android.synthetic.main.activity_favorited_cocktails.*

class FavoritedCocktailsActivity : AppCompatActivity() {
    lateinit var repository: FavoritedCocktailsRepository
    lateinit var viewModel: FavoritedCocktailsViewModel
    lateinit var myAdapter: FavoritedCocktailsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorited_cocktails)

        setUpRv()

        val db = AppDatabase.invoke(baseContext)
        repository = FavoritedCocktailsRepository(db)

        val viewModelProviderFactory =  FavoritesViewModelProviderFactory(repository)
       viewModel = ViewModelProvider(this, viewModelProviderFactory)[FavoritedCocktailsViewModel::class.java]


        
        viewModel.getSavedCocktails().observe(this, Observer {
            myAdapter.differ.submitList(it)
        })

        /*Swipe to delete*/
        val itemTouchCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val drink  = myAdapter.differ.currentList[position]
                viewModel.deleteCocktail(drink)

                Toast.makeText(baseContext, "Cocktail removed succesfully.", Toast.LENGTH_SHORT).show()
                }

            }
        ItemTouchHelper(itemTouchCallBack).apply {
            attachToRecyclerView(favoritesRecyclerView)
        }
        }



    private fun setUpRv() {
        myAdapter = FavoritedCocktailsAdapter()
        favoritesRecyclerView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(baseContext)
        }
    }
}