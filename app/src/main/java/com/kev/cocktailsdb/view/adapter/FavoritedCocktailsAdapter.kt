package com.kev.cocktailsdb.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kev.cocktailsdb.R
import com.kev.cocktailsdb.data.model.Drink
import com.kev.cocktailsdb.view.ui.CocktailDetailsActivity
import kotlinx.android.synthetic.main.cocktail_layout_file.view.*

class FavoritedCocktailsAdapter : RecyclerView.Adapter<FavoritedCocktailsAdapter.FavoritesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorites_item_layout, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val currentCocktail = differ.currentList[position]
        holder.bind(currentCocktail, holder.itemView.context)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, CocktailDetailsActivity::class.java)
            intent.putExtra("id", currentCocktail.idDrink)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int { return differ.currentList.size }

    class FavoritesViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(drink: Drink, context: Context) {
            itemView.tv_cocktail_name.text = drink.strDrink
            Glide.with(context).load(drink.strDrinkThumb).fitCenter().placeholder(R.drawable.loading).into(itemView.iv_cocktail_image)
        }

    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Drink>(){
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {

            return oldItem ==newItem
         }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

}