package com.example.shoppal.fragments

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppal.R
import com.example.shoppal.adapters.ShoppingItemsAdapter
import com.example.shoppal.adapters.ShoppingTypeAdapter
import com.example.shoppal.firebase.ShoppingItemsDatabase
import com.example.shoppal.models.Product


class ShoppingItemsFragment : Fragment(), View.OnClickListener {

    /**
     * Stores reference of searchEditText that takes keywords to search
     */
    private lateinit var searchEditText: EditText

    /**
     * Stores all shopping types in shoppingTypeList arraylist
     */
    private lateinit var shoppingTypeList: ArrayList<String>

    /**
     * Stores reference of recyclerview that displays shopping items
     */
    private lateinit var shoppingItemsRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_items, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchEditText = view.findViewById(R.id.edit_text_search)
        //Assigning shopping types in shoppingTypeList arraylist
        shoppingTypeList = ArrayList(
            listOf(
                "Business",
                "Cookbooks",
                "Mystery",
                "Scifi",
                "Accessories"
            )
        )
        val shoppingTypeRecyclerView =
            view.findViewById<RecyclerView>(R.id.recyclerview_shopping_type)
        shoppingTypeRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = ShoppingTypeAdapter(this@ShoppingItemsFragment, shoppingTypeList)
        }

        shoppingItemsRecyclerView =
            view.findViewById(R.id.recyclerview_shopping_items)
        shoppingItemsRecyclerView.layoutManager = GridLayoutManager(context, 2)
        //Reads all shopping items from ShoppingItemsDatabase and then updates UI
        ShoppingItemsDatabase(this).readDatabase(shoppingTypeList[0].lowercase())

        searchEditText.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                clearFocus()
            }
        }

        view.findViewById<ImageView>(R.id.btn_search).setOnClickListener(this)

        //Adding on touch listener which hides keyboard and clear search keywords
        view.findViewById<RecyclerView>(R.id.recyclerview_shopping_items).setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        searchEditText.clearFocus()
                    }
                }
                return v?.onTouchEvent(event) ?: true
            }
        })
    }

    /**
     * Refreshes recyclerview and update UI
     */
    fun updateUI(shoppingItemsList: ArrayList<Product>) {
        if (isAdded) {
            shoppingItemsRecyclerView.adapter =
                ShoppingItemsAdapter(requireContext(), shoppingItemsList)
        }
    }

    /**
     * Hides keyboard
     */
    private fun hideKeyboard(view: View) {
        try {
            val inputMethodManager =
                context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Clears search keyword
     */
    fun clearFocus(){
        searchEditText.setText("")
        searchEditText.clearFocus()
        hideKeyboard(searchEditText)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_search) {
                //If btn_search is clicked then refreshes UI by getting all items on the basis of search keyword
                val searchText = searchEditText.text.toString()
                searchEditText.clearFocus()
                if (searchText != "") {
                    ShoppingItemsDatabase(this).searchShoppingItem(searchText)
                    searchEditText.setText("")
                } else {
                    ShoppingItemsDatabase(this).readDatabase(shoppingTypeList[0].lowercase())
                    searchEditText.setText("")
                }
            }
        }
    }
}
