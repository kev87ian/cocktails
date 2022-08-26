package com.kev.cocktailsdb.view.ui.activities

import android.os.Bundle
import android.view.View
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
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorited_cocktails)
        setSupportActionBar(findViewById(R.id.my_toolbar_favorites))
            supportActionBar.apply {
                title = "Favorite Cocktails"
            }
        setUpRv()
        setupObserver()
        swipeToDelete()

       /* emptyRecordsImage.visibility = View.VISIBLE
        favoritesTvEmpty.visibility = View.VISIBLE*/


        if (myAdapter.differ.currentList.isEmpty()){
            emptyRecordsImage.visibility = View.VISIBLE
            favoritesTvEmpty.visibility = View.VISIBLE
        }

        else if(myAdapter.differ.currentList.isNotEmpty()) {

            emptyRecordsImage.visibility = View.GONE
            favoritesTvEmpty.visibility = View.GONE
        }


    }



    private fun swipeToDelete() {
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
                val drink = myAdapter.differ.currentList[position]
                viewModel.deleteCocktail(drink)
                val count = myAdapter.itemCount
                val finalCount = count - 1
                if (finalCount == 0) {
                    emptyRecordsImage.visibility = View.VISIBLE
                    favoritesTvEmpty.visibility = View.VISIBLE
                }

                if (finalCount != 0) {
                    emptyRecordsImage.visibility = View.GONE
                    favoritesTvEmpty.visibility = View.GONE
                }

            }

        }
        ItemTouchHelper(itemTouchCallBack).apply {
            attachToRecyclerView(favoritesRecyclerView)
        }
    }

    private fun setupObserver() {
        viewModel.getSavedCocktails().observe(this, Observer {
            myAdapter.differ.submitList(it)

        })

    }


    private fun setUpRv() {
        val db = AppDatabase.invoke(baseContext)
        repository = FavoritedCocktailsRepository(db)
        recyclerView = findViewById(R.id.favoritesRecyclerView)
        val viewModelProviderFactory = FavoritesViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[FavoritedCocktailsViewModel::class.java]

        myAdapter = FavoritedCocktailsAdapter()
        recyclerView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(baseContext)
        }
    }

}