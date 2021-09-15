package com.example.shoppal.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppal.R

class ShoppingTypeAdapter(private val context:Context, private val itemsTypeList:ArrayList<String>) : RecyclerView.Adapter<ShoppingTypeAdapter.ShoppingTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingTypeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_shopping_type, parent, false)
        return ShoppingTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingTypeViewHolder, position: Int) {
        holder.getTypeTextView().text = itemsTypeList[position]
    }

    override fun getItemCount(): Int {
        return itemsTypeList.size
    }

    class ShoppingTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val typeTextView = itemView.findViewById<TextView>(R.id.text_item_type)
        fun getTypeTextView() : TextView{
            return typeTextView
        }
    }
}