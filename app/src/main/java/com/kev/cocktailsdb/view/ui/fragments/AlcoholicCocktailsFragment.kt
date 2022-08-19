package com.kev.cocktailsdb.view.ui.fragments

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
import kotlinx.android.synthetic.main.fragment_alcoholic_cocktails.*

class AlcoholicCocktailsFragment : Fragment(R.layout.fragment_alcoholic_cocktails) {
    private lateinit var viewModel: CocktailsViewModel
    private lateinit var cocktailsAdapter: AlcoholicCocktailsAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        setUpObserver()

        retryNetworkCall()

    }

    override fun onResume() {
        super.onResume()
        setUpObserver()
        retryNetworkCall()
    }

    private fun retryNetworkCall() {
        retryButton.setOnClickListener{
            viewModel.getAlcoholicCocktails()
            setUpObserver()
        }
    }

    private fun setUpObserver() {
        viewModel.downloadedAlcoholResponse.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    cocktailsAdapter.differ.submitList(response.data?.drinks)
                    retryButton.visibility = View.GONE
                    errorTv.visibility = View.GONE
                }

                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    retryButton.visibility = View.GONE
                    errorTv.visibility = View.GONE
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    errorTv.text = response.message
                    errorTv.visibility = View.VISIBLE

                    retryButton.visibility = View.VISIBLE

                }
            }

        })
    }


    private fun setupRecyclerView() {
        cocktailsAdapter = AlcoholicCocktailsAdapter()
        alcoholicCocktailsRv.apply {
            adapter = cocktailsAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}