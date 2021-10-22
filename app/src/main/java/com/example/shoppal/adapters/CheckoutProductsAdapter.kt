package com.example.shoppal.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppal.R
import com.example.shoppal.room.entities.CartOrder

class CheckoutProductsAdapter(
    /**
     * Stores reference of context
     */
    private val context: Context,
    /**
     * Stores all lists of cart order in checkoutProductsList arraylist
     */
    private val checkoutProductsList: ArrayList<CartOrder>
) : RecyclerView.Adapter<CheckoutProductsAdapter.CheckoutProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutProductsViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_checkout_item, parent, false)
        return CheckoutProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckoutProductsViewHolder, position: Int) {
        //Sets image of cart order into imageview
        Glide.with(context).load(checkoutProductsList[position].itemImageUrl).into(holder.getCheckoutImageView())
        //Sets various cart details
        holder.getCheckoutNameTextView().text = checkoutProductsList[position].itemName
        holder.getCheckoutPriceTextView().text = checkoutProductsList[position].itemPrice.toString()
        holder.getCheckoutItemCountTextView().text =
            checkoutProductsList[position].itemCount.toString()
    }

    /**
     * Returns size of checkoutProductsList arrayList
     */
    override fun getItemCount(): Int {
        return checkoutProductsList.size
    }

    class CheckoutProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Initialises checkoutImageView that shows image of the order
         */
        private val checkoutImageView =
            itemView.findViewById<ImageView>(R.id.order_checkout_imageview)

        /**
         * Initialises checkoutNameTextView that shows name of the order
         */
        private val checkoutNameTextView =
            itemView.findViewById<TextView>(R.id.text_checkout_order_name)

        /**
         * Initialises checkoutPriceTextView that shows price of the order
         */
        private val checkoutPriceTextView =
            itemView.findViewById<TextView>(R.id.text_checkout_order_price)

        /**
         * Initialises checkoutItemCountTextView that shows no of the order
         */
        private val checkoutItemCountTextView =
            itemView.findViewById<TextView>(R.id.item_checkout_count)

        /**
         * Returns reference of checkoutImageView
         */
        fun getCheckoutImageView(): ImageView {
            return checkoutImageView
        }

        /**
         * Returns reference of checkoutNameTextView
         */
        fun getCheckoutNameTextView(): TextView {
            return checkoutNameTextView
        }

        /**
         * Returns reference of checkoutPriceTextView
         */
        fun getCheckoutPriceTextView(): TextView {
            return checkoutPriceTextView
        }

        /**
         * Returns reference of checkoutItemCountTextView
         */
        fun getCheckoutItemCountTextView(): TextView {
            return checkoutItemCountTextView
        }
    }
}