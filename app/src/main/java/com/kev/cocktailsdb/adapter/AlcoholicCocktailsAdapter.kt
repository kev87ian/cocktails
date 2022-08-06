package com.kev.cocktailsdb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.model.Drink
import kotlinx.android.synthetic.main.cocktail_layout_file.view.*

@Suppress("NAME_SHADOWING")
class AlcoholicCocktailsAdapter() :
    RecyclerView.Adapter<AlcoholicCocktailsAdapter.CocktailsViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cocktail_layout_file, parent, false)
        return CocktailsViewHolder(view)

    }


    override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {

        val currentCocktail = differ.currentList[position]
        holder.bind(currentCocktail, holder.itemView.context)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private var onItemClickListener: ((Drink) -> Unit)? = null

    class CocktailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(drink: Drink, context: Context) {

            itemView.tv_cocktail_name.text = drink.strDrink
            Glide.with(context).load(drink.strDrinkThumb).fitCenter()
                .placeholder(R.drawable.loading).into(itemView.iv_cocktail_image)


        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallBack)


    fun setOnItemClickListener(listener: (Drink) -> Unit) {
        onItemClickListener = listener
    }
}

