package com.example.shoppal.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.shoppal.R
import com.example.shoppal.activities.SelectAddressActivity
import com.example.shoppal.adapters.CartOrderDetailsAdapter
import com.example.shoppal.firebase.Firebase
import com.example.shoppal.room.daos.CartDao
import com.example.shoppal.room.databases.RoomDatabase
import com.example.shoppal.room.entities.CartOrder
import com.example.shoppal.utils.Constants
import java.util.*
import kotlin.collections.ArrayList


class CartFragment : Fragment() {

    /**
     * Stores the total price of cart orders
     */
    private var subTotal: Double = 0.00

    /**
     * Stores reference of cart dao that performs actions on cart products
     */
    private lateinit var cartDao: CartDao

    /**
     * Stores user id of the current user
     */
    private lateinit var currentUserId: String

    /**
     * Stores reference of ordersRecyclerView that shows all the orders' details
     */
    private lateinit var ordersRecyclerView: RecyclerView

    /**
     * Stores reference of textOrdersSubTotalTextView that shows total price of cart orders
     */
    private lateinit var textOrderSubtotalTextView: TextView

    /**
     * Stores reference of textChargeTextView that shows shipping charge
     */
    private lateinit var textChargeTextView: TextView

    /**
     * Stores reference of textTotalAmountTextView that shows total price of cart orders including shipping charge
     */
    private lateinit var textTotalAmountTextView: TextView

    /**
     * Stores all cart orders in cartOrdersList arraylist
     */
    private lateinit var cartOrdersList: ArrayList<CartOrder>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ordersRecyclerView = view.findViewById(R.id.recyclerview_orders)

        //Assigning user id of the current user
        currentUserId = Firebase(requireActivity()).currentUserId()
        //Initialises cartDao
        cartDao = Room.databaseBuilder(
            requireContext(),
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().cartDao()
        //Initialising various views
        textOrderSubtotalTextView = view.findViewById(R.id.text_order_subtotal)
        textChargeTextView = view.findViewById(R.id.text_charge)
        textTotalAmountTextView = view.findViewById(R.id.text_total_amount)

        //Adding on click listener on btn_checkout
        view.findViewById<View>(R.id.btn_checkout).setOnClickListener {
            if (subTotal != 0.00) {
                //if subtotal is not 0, then starts SelectAddressActivity
                val intent = Intent(context, SelectAddressActivity::class.java)
                intent.putExtra(Constants.DIRECT_BUY_STATUS, false)
                startActivity(Intent(context, SelectAddressActivity::class.java))
            }
        }
    }

    /**
     * Removes item from cart orders list and then updates recyclerview
     */
    fun removeItem(position: Int) {
        cartOrdersList.removeAt(position)
        ordersRecyclerView.adapter =
            CartOrderDetailsAdapter(this@CartFragment, cartOrdersList)
    }

    /**
     * Sets the amount details of cart orders
     */
    fun setAmountDetails(subtotal: Double, shippingCharge: Double) {
        textOrderSubtotalTextView.text = "$${String.format("%.2f", Math.abs(subtotal))}"
        textChargeTextView.text = "$${shippingCharge}"
        textTotalAmountTextView.text =
            "$${String.format("%.2f", Math.abs(subtotal) + shippingCharge)}"
    }

    /**
     * Returns total price of the cart orders
     */
    fun getSubtotal(): Double {
        return subTotal
    }

    /**
     * Sets subTotal
     */
    fun setSubtotal(subTotal: Double) {
        this.subTotal = subTotal
    }

    override fun onResume() {
        super.onResume()
        //Gets all the cart order from room database and store them in cartOrdersList arraylist
        cartOrdersList = cartDao.getAllOrders(currentUserId) as ArrayList<CartOrder>
        ordersRecyclerView.apply {
            this.adapter = CartOrderDetailsAdapter(
                this@CartFragment, cartOrdersList
            )
            this.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        //Calculating total price of all cart orders
        subTotal = 0.0
        for (cartOrder in cartOrdersList) {
            subTotal += cartOrder.itemPrice!!
        }
        if (subTotal == 0.00) {
            setAmountDetails(subTotal, 0.0)
        } else {
            setAmountDetails(subTotal, 15.0)
        }
    }
}