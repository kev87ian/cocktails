package com.kev.cocktailsdb.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kev.cocktailsdb.HiltApplication
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.adapter.AlcoholicCocktailsAdapter
import com.kev.cocktailsdb.repository.MainRepository
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.viewmodel.MainViewModel
import com.kev.cocktailsdb.viewmodel.MainViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.errorTVMain
import kotlinx.android.synthetic.main.activity_main.paginationProgressBar
import kotlinx.android.synthetic.main.activity_nalcoholic_cocktails.*

class NAlcoholicCocktailsActivity : AppCompatActivity() {
    private lateinit var mAdapter: AlcoholicCocktailsAdapter
    private lateinit var mViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nalcoholic_cocktails)

        initBottomNav()
        initUI()

    }

    private fun initUI() {
        mAdapter = AlcoholicCocktailsAdapter(applicationContext)
        nAlcoholicCocktailsRv.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(this@NAlcoholicCocktailsActivity, 2)
            setHasFixedSize(true)
        }


        val repository = MainRepository()
        val viewModelProviderFactory = MainViewModelProviderFactory(application as HiltApplication, repository)
        mViewModel = ViewModelProvider(this, viewModelProviderFactory)[MainViewModel::class.java]


        mViewModel.nDownloadedAlcoholResponse.observe(this, Observer{response->
            when(response){
                is Resource.Success ->{
                    paginationProgressBarr.visibility = View.GONE
                    mAdapter.differ.submitList(response.data?.drinks)
               //     Toast.makeText(this@NAlcoholicCocktailsActivity, "Loaded", Toast.LENGTH_SHORT).show()
                }

                is Resource.Error ->{
                    paginationProgressBarr.visibility = View.GONE
                    Toast.makeText(this@NAlcoholicCocktailsActivity, "${response.message}", Toast.LENGTH_SHORT).show()
                    errorTV.visibility = View.VISIBLE
                    errorTV.text = response.message

                }

                is Resource.Loading ->{
                    paginationProgressBarr.visibility = View.VISIBLE
                    Toast.makeText(this@NAlcoholicCocktailsActivity, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        })


    }


    private fun initBottomNav() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.NAlcoholicCocktailsActivity
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.alcoholicCocktailsActivity -> {
                    startActivity(
                        Intent(
                            applicationContext,
                            MainActivity::class.java
                        )
                    )
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
//                }
            }
            false
        })
    }


}