package com.kev.cocktailsdb.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.data.repository.SearchCocktailsRepository
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.view.adapter.CocktailSearchAdapter
import com.kev.cocktailsdb.view.viewmodelprovider.CocktailSearchViewModelProviderFactory
import com.kev.cocktailsdb.viewmodel.CocktailSearchViewModel
import kotlinx.android.synthetic.main.activity_search_cocktail.*

class CocktailSearchActivity : AppCompatActivity() {
    lateinit var viewModel: CocktailSearchViewModel
    lateinit var repository: SearchCocktailsRepository
    private lateinit var myAdapter: CocktailSearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_cocktail)

        setUpRecyclerView()

        val cocktailName = intent.getStringExtra("userQuery").toString()

        repository = SearchCocktailsRepository()
        val viewModelProviderFactory = CocktailSearchViewModelProviderFactory(
            repository,
            cocktailName,
            application as HiltApplication
        )
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[CocktailSearchViewModel::class.java]

        viewModel.searchCocktailsResponse.observe(this) { response ->

            when (response) {
                is Resource.Success -> {
                    progressBar.visibility = View.GONE

                    if (!response.data?.drinks.isNullOrEmpty()) {
                        myAdapter.differ.submitList(response.data?.drinks)

                    } else {
                        errorTv.visibility = View.VISIBLE

                    }

                }
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE

                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    errorTv.visibility = View.VISIBLE
                    errorTv.text = response.message
                    Toast.makeText(baseContext, response.message, Toast.LENGTH_SHORT).show()
                }

            }

        }
    }


    private fun setUpRecyclerView() {
        myAdapter = CocktailSearchAdapter()
        cocktailSearchRV.apply {
            adapter = myAdapter
            layoutManager = GridLayoutManager(baseContext, 2)

        }
    }
}