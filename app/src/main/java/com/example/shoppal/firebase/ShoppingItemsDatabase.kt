package com.example.shoppal.firebase

import android.util.Log
import com.example.shoppal.fragments.ShoppingItemsFragment
import com.example.shoppal.models.Product
import com.example.shoppal.utils.Constants
import com.example.shoppal.utils.Tags
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class ShoppingItemsDatabase(
    private val shoppingItemsFragment: ShoppingItemsFragment?
) {
    /**
     * Stores reference of databaseInstance
     */
    private val databaseInstance =
        FirebaseDatabase.getInstance("https://shoppal-42b45-default-rtdb.asia-southeast1.firebasedatabase.app")

    /**
     * Stores key of the child snapshot in database snapshot
     */
    private var key: String = ""

    /**
     * Gets all shopping items from database of a particular category and then update UI
     */
    fun readDatabase(category: String) {
        val databaseRef = databaseInstance.getReference(Constants.PRODUCTS).orderByChild("category")
            .equalTo(category)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val shoppingItemsList = ArrayList<Product>()
                for (childSnapshot in dataSnapshot.children) {
                    key = childSnapshot.key.toString()
                    shoppingItemsList.add(childSnapshot.getValue<Product>()!!)
                    Log.d(Tags.READ_DATABASE_STATUS, "key is: $key")
                }
                shoppingItemsFragment!!.updateUI(shoppingItemsList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(Tags.READ_DATABASE_STATUS, "Failed to read value.", error.toException())
            }
        })
    }

    /**
     * Gets all shopping items based on the search key word and then update UI
     */
    fun searchShoppingItem(item: String) {
        val databaseRef =
            databaseInstance.getReference(Constants.PRODUCTS).orderByChild("name").startAt("%${item}%")
                .endAt(item+"\uf8ff")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val shoppingItemsList = ArrayList<Product>()
                for (childSnapshot in dataSnapshot.children) {
                    key = childSnapshot.key.toString()
                    val product = childSnapshot.getValue<Product>()
                    if(product!!.name.lowercase().contains(item.lowercase())){
                        shoppingItemsList.add(product)
                    }
                    Log.d(Tags.READ_DATABASE_STATUS, "key is: $key")
                }
                shoppingItemsFragment!!.updateUI(shoppingItemsList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(Tags.READ_DATABASE_STATUS, "Failed to read value.", error.toException())
            }
        })
    }
}