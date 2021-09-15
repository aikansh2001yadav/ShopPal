package com.example.shoppal.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppal.R
import com.example.shoppal.activities.ItemOverviewActivity

class ShoppingItemsAdapter(private val context: Context, private val itemsList: ArrayList<String>) :
    RecyclerView.Adapter<ShoppingItemsAdapter.ShoppingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_shopping_item, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        holder.getItemImageView().setOnClickListener {
            context.startActivity(Intent(context, ItemOverviewActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
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
