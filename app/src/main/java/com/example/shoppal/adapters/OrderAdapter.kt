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
import com.example.shoppal.models.Order

class OrderAdapter(private val context:Context, private val orderList:ArrayList<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_checkout_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        //Sets image of order into imageview
        Glide.with(context).load(orderList[position].img).into(holder.getCheckoutImageView())
        //Sets various details of order
        holder.getCheckoutNameTextView().text = orderList[position].name
        holder.getCheckoutPriceTextView().text = orderList[position].price.toString()
        holder.getCheckoutItemCountTextView().text = orderList[position].orderCount.toString()
    }

    /**
     * Returns size of the ordersList arraylist
     */
    override fun getItemCount(): Int {
        return orderList.size
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        /**
         * Initialises orderImageView that shows image of the order
         */
        private val orderImageView =
            itemView.findViewById<ImageView>(R.id.order_checkout_imageview)

        /**
         * Initialises orderNameTextView that shows name of the order
         */
        private val orderNameTextView =
            itemView.findViewById<TextView>(R.id.text_checkout_order_name)

        /**
         * Initialises orderPriceTextView that shows price of the order
         */
        private val orderPriceTextView =
            itemView.findViewById<TextView>(R.id.text_checkout_order_price)

        /**
         * Initialises orderCountTextView that shows no of order item to be bought
         */
        private val orderCountTextView =
            itemView.findViewById<TextView>(R.id.item_checkout_count)

        /**
         * Returns reference of orderImageView
         */
        fun getCheckoutImageView(): ImageView {
            return orderImageView
        }

        /**
         * Returns reference of orderNameTextView
         */
        fun getCheckoutNameTextView(): TextView {
            return orderNameTextView
        }

        /**
         * Returns reference of orderPriceTextView
         */
        fun getCheckoutPriceTextView(): TextView {
            return orderPriceTextView
        }

        /**
         * Returns reference of orderCountTextView
         */
        fun getCheckoutItemCountTextView(): TextView {
            return orderCountTextView
        }
    }
}