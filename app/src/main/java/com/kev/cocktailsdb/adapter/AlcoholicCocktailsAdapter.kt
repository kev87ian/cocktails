package com.kev.cocktailsdb.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.databinding.CocktailLayoutFileBinding
import com.kev.cocktailsdb.model.Drink
import com.kev.cocktailsdb.ui.CocktailDetailsActivity

class AlcoholicCocktailsAdapter(private val context: Context) :
    RecyclerView.Adapter<AlcoholicCocktailsAdapter.CocktailsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {

        return CocktailsViewHolder(
            CocktailLayoutFileBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {

        val currentCocktail = differ.currentList[position]
        holder.binding.apply {

            tvCocktailName.text = currentCocktail.strDrink
            Glide.with(holder.itemView.context).load(currentCocktail.strDrinkThumb)
                .placeholder(R.drawable.loading).fitCenter().into(ivCocktailImage)
        }
//
//        holder.itemView.setOnClickListener {
//            val intent = Intent(context, CocktailDetailsActivity::class.java)
//            intent.putExtra("idDrink", currentCocktail.idDrink)
//            intent.putExtra("drinkName", currentCocktail.strDrink)
//            intent.putExtra("drinkImage", currentCocktail.strDrinkThumb)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(intent)
//            currentCocktail?.strDrink?.let { it -> Log.d("OnClick", it) }
//           Log.d("onclick", currentCocktail.idDrink.toString())
//        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class CocktailsViewHolder(val binding: CocktailLayoutFileBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


    private val diffCallBack = object : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallBack)
//    var cocktails:List<Drink>
//    get() = differ.currentList
//    set(value) {
//        differ.submitList(value)
//    }

}

