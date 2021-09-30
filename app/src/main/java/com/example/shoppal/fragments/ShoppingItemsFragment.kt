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
import com.example.shoppal.firebase.ShoppingItemsDatabase
import com.example.shoppal.models.Product

class ShoppingItemsFragment : Fragment() {

    private lateinit var shoppingItemsRecyclerView: RecyclerView
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
                "Scifi",
                "Accessories"
            )
        )
        // access the listView from xml file
        val shoppingTypeRecyclerView =
            view.findViewById<RecyclerView>(R.id.recyclerview_shopping_type)
        shoppingTypeRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = ShoppingTypeAdapter(this@ShoppingItemsFragment, shoppingTypeList)
        }

        shoppingItemsRecyclerView =
            view.findViewById(R.id.recyclerview_shopping_items)
        shoppingItemsRecyclerView.layoutManager = GridLayoutManager(context, 2)
        ShoppingItemsDatabase(this).readDatabase(shoppingTypeList[0].lowercase())
    }

    fun updateUI(shoppingItemsList: ArrayList<Product>) {
        if (isAdded) {
            shoppingItemsRecyclerView.adapter =
                ShoppingItemsAdapter(requireContext(), shoppingItemsList)
        }
    }
}