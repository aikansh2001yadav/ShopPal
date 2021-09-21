package com.example.shoppal.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppal.R
import com.example.shoppal.activities.ItemOverviewActivity
import com.example.shoppal.models.Product
import com.example.shoppal.utils.Constants

class ShoppingItemsAdapter(
    private val context: Context,
    private val shoppingItemsList: ArrayList<Product>
) :
    RecyclerView.Adapter<ShoppingItemsAdapter.ShoppingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_shopping_item, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        Glide.with(context).load(shoppingItemsList[position].img).into(holder.getItemImageView())
        holder.getItemNameTextView().text = shoppingItemsList[position].name
        holder.getPriceTextView().text = shoppingItemsList[position].price.toString()
        holder.getItemImageView().setOnClickListener {
            val intent = Intent(context, ItemOverviewActivity::class.java)
            val bundle = Bundle()
            val parcel = shoppingItemsList[position]
            bundle.putParcelable(Constants.ITEM_DATA, parcel)
            intent.putExtra(Constants.SHOPPING_ITEM_BUNDLE, bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return shoppingItemsList.size
    }

    class ShoppingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemImageView = itemView.findViewById<ImageView>(R.id.item_imageview)
        private val itemNameTextView = itemView.findViewById<TextView>(R.id.text_item_name)
        private val priceTextView = itemView.findViewById<TextView>(R.id.text_item_price)

        fun getItemImageView(): ImageView {
            return itemImageView
        }

        fun getItemNameTextView(): TextView {
            return itemNameTextView
        }

        fun getPriceTextView(): TextView {
            return priceTextView
        }
    }
}
