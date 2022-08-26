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
import com.kev.cocktailsdb.view.ui.activities.CocktailDetailsActivity
import kotlinx.android.synthetic.main.cocktail_layout_file.view.*

@Suppress("NAME_SHADOWING")
class AlcoholicCocktailsAdapter() :
    RecyclerView.Adapter<AlcoholicCocktailsAdapter.CocktailsViewHolder>() {

    class CocktailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(drink: Drink, context: Context) {

            itemView.tv_cocktail_name.text = drink.strDrink
            Glide.with(context).load(drink.strDrinkThumb).fitCenter().placeholder(R.drawable.loading).into(itemView.iv_cocktail_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cocktail_layout_file, parent, false)
        return CocktailsViewHolder(view)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {

        val currentCocktail = differ.currentList[position]
        holder.bind(currentCocktail, holder.itemView.context)

      holder.itemView.setOnClickListener{
        val intent = Intent(holder.itemView.context, CocktailDetailsActivity::class.java)
          intent.putExtra("id", currentCocktail.idDrink)
          intent.putExtra("name", currentCocktail.strDrink)
          holder.itemView.context.startActivity(intent)
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


}

