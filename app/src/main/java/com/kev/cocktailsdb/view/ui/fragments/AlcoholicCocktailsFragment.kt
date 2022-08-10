package com.kev.cocktailsdb.view.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.view.adapter.AlcoholicCocktailsAdapter
import com.kev.cocktailsdb.view.ui.MainActivity
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.viewmodel.CocktailsViewModel
import kotlinx.android.synthetic.main.fragment_alcoholic_cocktails.*

class AlcoholicCocktailsFragment : Fragment(R.layout.fragment_alcoholic_cocktails) {
    private lateinit var viewModel: CocktailsViewModel
    private lateinit var cocktailsAdapter: AlcoholicCocktailsAdapter

    private val TAG = "AlcoholicCocktailsFragment"

    @SuppressLint("LongLogTag")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()


        viewModel.downloadedAlcoholResponse.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    progressBarr.visibility = View.GONE
                    cocktailsAdapter.differ.submitList(response.data?.drinks)
                }

                is Resource.Loading -> {
                    progressBarr.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    progressBarr.visibility = View.GONE
                    errorTV.text = response.message
                    errorTV.visibility = View.VISIBLE
                    Log.d(TAG, response.message.toString())

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