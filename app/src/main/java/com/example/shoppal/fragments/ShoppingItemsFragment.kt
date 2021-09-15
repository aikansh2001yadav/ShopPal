package com.example.shoppal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppal.R
import com.example.shoppal.adapters.ShoppingItemsAdapter
import com.example.shoppal.adapters.ShoppingTypeAdapter

class ShoppingItemsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shoppingTypeList = ArrayList<String>(
            listOf(
                "Business",
                "Cookbooks",
                "Mystery",
                "Sci-fi",
                "Accessories"
            )
        )
        // access the listView from xml file
        val shoppingTypeRecyclerView =
            view.findViewById<RecyclerView>(R.id.recyclerview_shopping_type)
        shoppingTypeRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = ShoppingTypeAdapter(context, shoppingTypeList)
        }

        val shoppingRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_shopping_items)
        val itemList = ArrayList<String>()
        for (i in 0..15) {
            itemList.add("hello")
        }
        shoppingRecyclerView.apply {
            this.layoutManager = GridLayoutManager(context, 2)
            this.adapter = ShoppingItemsAdapter(context, itemList)
        }
    }
}