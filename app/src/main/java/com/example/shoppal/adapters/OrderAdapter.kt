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
import com.example.shoppal.models.Product

class OrderAdapter(private val context:Context, private val orderList:ArrayList<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_checkout_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        Glide.with(context).load(orderList[position].img).into(holder.getCheckoutImageView())
        holder.getCheckoutNameTextView().text = orderList[position].name
        holder.getCheckoutPriceTextView().text = orderList[position].price.toString()
        holder.getCheckoutItemCountTextView().text = orderList[position].orderCount.toString()
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val orderImageView =
            itemView.findViewById<ImageView>(R.id.order_checkout_imageview)
        private val orderNameTextView =
            itemView.findViewById<TextView>(R.id.text_checkout_order_name)
        private val orderPriceTextView =
            itemView.findViewById<TextView>(R.id.text_checkout_order_price)
        private val orderCountTextView =
            itemView.findViewById<TextView>(R.id.item_checkout_count)

        fun getCheckoutImageView(): ImageView {
            return orderImageView
        }

        fun getCheckoutNameTextView(): TextView {
            return orderNameTextView
        }

        fun getCheckoutPriceTextView(): TextView {
            return orderPriceTextView
        }

        fun getCheckoutItemCountTextView(): TextView {
            return orderCountTextView
        }
    }
}