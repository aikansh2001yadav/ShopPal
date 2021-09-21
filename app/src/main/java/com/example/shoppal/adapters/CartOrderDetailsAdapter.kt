package com.example.shoppal.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppal.R

class CartOrderDetailsAdapter(
    private val context: Context,
    private val cardDetailsList: ArrayList<String>
) : RecyclerView.Adapter<CartOrderDetailsAdapter.CardOrderDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardOrderDetailsViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_card_order_item, parent, false)
        return CardOrderDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardOrderDetailsViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return cardDetailsList.size
    }

    class CardOrderDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderImageView = itemView.findViewById<ImageView>(R.id.order_imageview)
        private val orderNameTextView = itemView.findViewById<TextView>(R.id.text_order_name)
        private val orderPriceTextView = itemView.findViewById<TextView>(R.id.text_order_price)
        private val subtractButton = itemView.findViewById<ImageButton>(R.id.btn_subtract)
        private val countTextView = itemView.findViewById<TextView>(R.id.text_count)
        private val sumButton = itemView.findViewById<ImageButton>(R.id.btn_sum)
        private val deleteButton = itemView.findViewById<ImageButton>(R.id.btn_delete)

        fun getOrderImageView(): ImageView {
            return orderImageView
        }

        fun getOrderNameTextView(): TextView {
            return orderNameTextView
        }

        fun getOrderPriceTextView(): TextView {
            return orderPriceTextView
        }

        fun getSubtractButton(): ImageButton {
            return subtractButton
        }

        fun getCountTextView(): TextView {
            return countTextView
        }

        fun getSumButton(): ImageButton {
            return sumButton
        }

        fun getDeleteButton(): ImageButton {
            return deleteButton
        }
    }
}