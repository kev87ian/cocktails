package com.kev.cocktailsdb.ui

import androidx.appcompat.app.AppCompatActivity

class NAlcoholicCocktailsActivity : AppCompatActivity() {
//    lateinit var mAdapter: AlcoholicCocktailsAdapter
//    private lateinit var mViewModel: MainViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_nalcoholic_cocktails)
//
//        initBottomNav()
//        initUi()
//
//    }
//
//    private fun initUi() {
//        mAdapter = AlcoholicCocktailsAdapter()
//        nAlcoholicCocktailsRv.apply {
//            layoutManager = GridLayoutManager(this@NAlcoholicCocktailsActivity, 2)
//            setHasFixedSize(true)
//            adapter = mAdapter
//        }
//
//        val repository = MainRepository()
//        val viewModelProviderFactory = MainViewModelProviderFactory(repository)
//        mViewModel = ViewModelProvider(this, viewModelProviderFactory)[MainViewModel::class.java]
//
//        mViewModel.downloadedNanAlcoholResponse.observe(this) { response ->
//            when (response) {
//                is Resource.Success -> {
//                    response.data?.let { cocktailResponse ->
//                        mAdapter.differ.submitList(cocktailResponse.drinks)
//                        hideProgressBar()
//                    }
//
//                }
//
//                is Resource.Error -> {
//                    hideProgressBar()
//                    errorTV.visibility = View.VISIBLE
//                    response.message.let { message ->
//                        Log.e("Something went wrong", message.toString())
//                    }
//
//                }
//
//                is Resource.Loading -> {
//                    showProgressBar()
//                }
//            }
//        }
//    }
//
//
//    private fun hideProgressBar() {
//        paginationProgressBar.visibility = View.GONE
//    }
//
//    private fun showProgressBar() {
//        paginationProgressBar.visibility = View.VISIBLE
//    }
//
//
//    private fun initBottomNav() {
//        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
//        bottomNavigationView.selectedItemId = R.id.NAlcoholicCocktailsActivity
//        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//
//                R.id.alcoholicCocktailsActivity -> {
//                    startActivity(Intent(applicationContext, MainActivity::class.java))
//                    overridePendingTransition(0, 0)
//                    return@OnNavigationItemSelectedListener true
//                }
//
////                android.R.id.maleActivity -> {
////                    startActivity(Intent(applicationContext, Male::class.java))
////                    overridePendingTransition(0, 0)
////                    return@OnNavigationItemSelectedListener true
////                }
//            }
//            false
//        })
//    }


}