package com.kev.cocktailsdb.view.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.view.adapter.AlcoholicCocktailsAdapter
import com.kev.cocktailsdb.view.ui.MainActivity
import com.kev.cocktailsdb.viewmodel.CocktailsViewModel
import kotlinx.android.synthetic.main.fragment_nalcoholic_cocktails.*

class NAlcoholicCocktailsFragment : Fragment(R.layout.fragment_nalcoholic_cocktails) {
    private lateinit var viewModel: CocktailsViewModel
    private lateinit var cocktailsAdapter: AlcoholicCocktailsAdapter



    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        setupObserver()
        retryNetworkCall()
    }

    private fun retryNetworkCall() {
        btnRetry.setOnClickListener {
            viewModel.getNAlcoholicCocktails()
            setupObserver()
        }
    }

    override fun onResume() {
        super.onResume()
        setupObserver()
        retryNetworkCall()
    }
    private fun setupObserver() {
        viewModel.nDownloadedAlcoholResponse.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Loading ->{
                    progressBarN.visibility = View.VISIBLE
                    errorTvN.visibility = View.GONE
                    btnRetry.visibility =View.GONE
                }

                is Resource.Error ->{
                    progressBarN.visibility = View.GONE
                    errorTvN.text = response.message
                    errorTvN.visibility = View.VISIBLE
                    btnRetry.visibility = View.VISIBLE

                }

                is Resource.Success ->{
                    progressBarN.visibility = View.GONE
                    errorTvN.visibility = View.GONE
                    btnRetry.visibility = View.GONE
                    cocktailsAdapter.differ.submitList(response.data?.drinks)
                }
            }


        })
    }

    private fun setupRecyclerView() {
        cocktailsAdapter = AlcoholicCocktailsAdapter()
        nAlcoholicCocktailsRv.apply {
            adapter = cocktailsAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}