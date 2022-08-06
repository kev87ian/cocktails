package com.kev.cocktailsdb.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.adapter.AlcoholicCocktailsAdapter
import com.kev.cocktailsdb.ui.MainActivity
import com.kev.cocktailsdb.util.Resource
import com.kev.cocktailsdb.viewmodel.CocktailsViewModel
import kotlinx.android.synthetic.main.fragment_alcoholic_cocktails.*
import kotlinx.android.synthetic.main.fragment_nalcoholic_cocktails.*

class NAlcoholicCocktailsFragment : Fragment(R.layout.fragment_nalcoholic_cocktails) {
    private lateinit var viewModel: CocktailsViewModel
    private lateinit var cocktailsAdapter: AlcoholicCocktailsAdapter

    val TAG = "NonAlcoholicCocktailsFragment"

    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        cocktailsAdapter.setOnItemClickListener {
            cocktailsAdapter.setOnItemClickListener {
                val bundle = Bundle().apply {
                    putSerializable("drink", it.idDrink)
                }
                findNavController().navigate(
                    R.id.action_NAlcoholicCocktailsFragment_to_cocktailDetailsFragment, bundle
                )
            }
        }

        viewModel.nDownloadedAlcoholResponse.observe(viewLifecycleOwner, Observer {response->

            when(response){
                is Resource.Loading ->{
                    progressBarN.visibility = View.VISIBLE

                }

                is Resource.Error ->{
                    progressBarN.visibility = View.GONE
                    errorTvN.text = response.message
                    errorTV.visibility = View.VISIBLE
                    Log.d(TAG, response.message.toString())
                }

                is Resource.Success ->{
                    progressBarN.visibility = View.GONE
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