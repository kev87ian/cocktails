package com.kev.cocktailsdb.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.adapter.AlcoholicCocktailsAdapter
import com.kev.cocktailsdb.repository.MainRepository
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.viewmodel.MainViewModel
import com.kev.cocktailsdb.viewmodel.MainViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.paginationProgressBar
import kotlinx.android.synthetic.main.activity_nalcoholic_cocktails.*


class MainActivity : AppCompatActivity() {
    lateinit var mAdapter: AlcoholicCocktailsAdapter
    private lateinit var mViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomNav()
        initUI()

    }

    private fun initUI() {
        mAdapter = AlcoholicCocktailsAdapter()
        alcoholicCocktailsRv.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)
        }



        val repository = MainRepository()
        val viewModelProviderFactory = MainViewModelProviderFactory(repository)
        mViewModel = ViewModelProvider(this, viewModelProviderFactory)[MainViewModel::class.java]

        mViewModel.downloadedAlcoholResponse.observe(this) { response ->

            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    errorTV.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    response.data?.let {
                        mAdapter.differ.submitList(it.drinks)
                        hideProgressBar()
                    }
                }
            }


        }
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }


    private fun initBottomNav() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.alcoholicCocktailsActivity
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.NAlcoholicCocktailsActivity -> {
                    startActivity(
                        Intent(
                            applicationContext,
                            NAlcoholicCocktailsActivity::class.java
                        )
                    )
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }

//                android.R.id.maleActivity -> {
//                    startActivity(Intent(applicationContext, Male::class.java))
//                    overridePendingTransition(0, 0)
//                    return@OnNavigationItemSelectedListener true
//                }
            }
            false
        })
    }


}