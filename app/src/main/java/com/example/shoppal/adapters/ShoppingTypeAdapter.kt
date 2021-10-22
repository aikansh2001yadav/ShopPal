package com.example.shoppal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppal.R
import com.example.shoppal.firebase.ShoppingItemsDatabase
import com.example.shoppal.fragments.ShoppingItemsFragment

class ShoppingTypeAdapter(private val shoppingItemsFragment:ShoppingItemsFragment, private val itemsTypeList:ArrayList<String>) : RecyclerView.Adapter<ShoppingTypeAdapter.ShoppingTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingTypeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_shopping_type, parent, false)
        return ShoppingTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingTypeViewHolder, position: Int) {
        //Sets category text
        holder.getTypeTextView().text = itemsTypeList[position]
        //Adding on click listener on typeTextView that refreshes shopping items bases on the selected category
        holder.getTypeTextView().setOnClickListener {
            shoppingItemsFragment.clearFocus()
            ShoppingItemsDatabase(shoppingItemsFragment).readDatabase(itemsTypeList[position].lowercase())
        }
    }

    /**
     * Returns size of itemsTypeList arraylist
     */
    override fun getItemCount(): Int {
        return itemsTypeList.size
    }

    class ShoppingTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        /**
         * Initialises typeTextView that shows category text
         */
        private val typeTextView = itemView.findViewById<TextView>(R.id.text_item_type)

        /**
         * Returns reference of typeTextView
         */
        fun getTypeTextView() : TextView{
            return typeTextView
        }

    }
}