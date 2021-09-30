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
    private val context: Context,
    private val checkoutProductsList: ArrayList<CartOrder>
) : RecyclerView.Adapter<CheckoutProductsAdapter.CheckoutProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutProductsViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_checkout_item, parent, false)
        return CheckoutProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckoutProductsViewHolder, position: Int) {
        Glide.with(context).load(checkoutProductsList[position].itemImageUrl).into(holder.getCheckoutImageView())
        holder.getCheckoutNameTextView().text = checkoutProductsList[position].itemName
        holder.getCheckoutPriceTextView().text = checkoutProductsList[position].itemPrice.toString()
        holder.getCheckoutItemCountTextView().text =
            checkoutProductsList[position].itemCount.toString()
    }

    override fun getItemCount(): Int {
        return checkoutProductsList.size
    }

    class CheckoutProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkoutImageView =
            itemView.findViewById<ImageView>(R.id.order_checkout_imageview)
        private val checkoutNameTextView =
            itemView.findViewById<TextView>(R.id.text_checkout_order_name)
        private val checkoutPriceTextView =
            itemView.findViewById<TextView>(R.id.text_checkout_order_price)
        private val checkoutItemCountTextView =
            itemView.findViewById<TextView>(R.id.item_checkout_count)

        fun getCheckoutImageView(): ImageView {
            return checkoutImageView
        }

        fun getCheckoutNameTextView(): TextView {
            return checkoutNameTextView
        }

        fun getCheckoutPriceTextView(): TextView {
            return checkoutPriceTextView
        }

        fun getCheckoutItemCountTextView(): TextView {
            return checkoutItemCountTextView
        }
    }
}