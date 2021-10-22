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
    /**
     * Stores reference of context
     */
    private val context: Context,
    /**
     * Stores reference of shoppingItemsList arraylist
     */
    private val shoppingItemsList: ArrayList<Product>
) :
    RecyclerView.Adapter<ShoppingItemsAdapter.ShoppingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_shopping_item, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        //Sets imageview of shopping item into imageview
        Glide.with(context).load(shoppingItemsList[position].img).into(holder.getItemImageView())
        //Sets various shopping item details
        holder.getItemNameTextView().text = shoppingItemsList[position].name
        holder.getPriceTextView().text = shoppingItemsList[position].price.toString()
        //Adding on click listener on itemImageView that starts ItemOverviewActivity
        holder.getItemImageView().setOnClickListener {
            val intent = Intent(context, ItemOverviewActivity::class.java)
            val bundle = Bundle()
            val parcel = shoppingItemsList[position]
            bundle.putParcelable(Constants.ITEM_DATA, parcel)
            intent.putExtra(Constants.SHOPPING_ITEM_BUNDLE, bundle)
            context.startActivity(intent)
        }
    }

    /**
     * Returns the size of shopping items list
     */
    override fun getItemCount(): Int {
        return shoppingItemsList.size
    }

    class ShoppingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Initialises itemImageView that shows image of the item
         */
        private val itemImageView = itemView.findViewById<ImageView>(R.id.item_imageview)

        /**
         * Initialises itemNameTextView that shows name of the shopping item
         */
        private val itemNameTextView = itemView.findViewById<TextView>(R.id.text_item_name)

        /**
         * Initialises priceTextView that shows price of the shopping item
         */
        private val priceTextView = itemView.findViewById<TextView>(R.id.text_item_price)

        /**
         * Returns reference of itemImageView
         */
        fun getItemImageView(): ImageView {
            return itemImageView
        }

        /**
         * Returns reference of itemNameTextView
         */
        fun getItemNameTextView(): TextView {
            return itemNameTextView
        }

        /**
         * Returns reference of priceTextView
         */
        fun getPriceTextView(): TextView {
            return priceTextView
        }

    }
}
