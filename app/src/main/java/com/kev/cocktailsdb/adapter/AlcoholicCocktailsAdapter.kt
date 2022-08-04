package com.kev.cocktailsdb.adapter

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
import com.kev.cocktailsdb.model.Drink
import com.kev.cocktailsdb.ui.CocktailDetailsActivity
import kotlinx.android.synthetic.main.cocktail_layout_file.view.*

@Suppress("NAME_SHADOWING")
class AlcoholicCocktailsAdapter(private val context: Context) :
    RecyclerView.Adapter<AlcoholicCocktailsAdapter.CocktailsViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cocktail_layout_file, parent, false)
        return CocktailsViewHolder(view)

    }


    override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {

        val currentCocktail = differ.currentList[position]
        holder.bind(currentCocktail, context)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class CocktailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(drink: Drink, context: Context) {

            itemView.tv_cocktail_name.text = drink.strDrink
            Glide.with(context).load(drink.strDrinkThumb).fitCenter()
                .placeholder(R.drawable.loading).into(itemView.iv_cocktail_image)

            itemView.setOnClickListener {
                val intent = Intent(context, CocktailDetailsActivity::class.java)
                intent.putExtra("idDrink", drink.idDrink)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

            }

        }
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

