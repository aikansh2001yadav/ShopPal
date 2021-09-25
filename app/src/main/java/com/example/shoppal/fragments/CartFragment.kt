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
import com.example.shoppal.activities.AddressActivity
import com.example.shoppal.adapters.CartOrderDetailsAdapter
import com.example.shoppal.firebase.Firebase
import com.example.shoppal.room.databases.CartOrderDatabase
import com.example.shoppal.room.entities.CartOrder
import java.util.*
import kotlin.collections.ArrayList


class CartFragment : Fragment() {

    private var subTotal: Double = 0.00
    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var textOrderSubtotalTextView: TextView
    private lateinit var textChargeTextView: TextView
    private lateinit var textTotalAmountTextView: TextView
    private lateinit var cartOrdersQuantity: ArrayList<Int>
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
        ordersRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_orders)

        val currentUserId: String = Firebase(requireActivity()).currentUserId()
        val db = Room.databaseBuilder(
            requireContext(),
            CartOrderDatabase::class.java,
            "cart_database"
        ).allowMainThreadQueries().build()
        val cartDao = db.cartDao()
        cartOrdersList = cartDao.getAllOrders(currentUserId) as ArrayList<CartOrder>
        cartOrdersQuantity = arrayListOf(*Array(cartOrdersList.size) { 1 })
        ordersRecyclerView.apply {
            this.adapter = CartOrderDetailsAdapter(
                this@CartFragment, cartOrdersList, cartOrdersQuantity
            )
            this.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        textOrderSubtotalTextView = view.findViewById(R.id.text_order_subtotal)
        textChargeTextView = view.findViewById(R.id.text_charge)
        textTotalAmountTextView = view.findViewById(R.id.text_total_amount)
        for (cartOrder in cartOrdersList) {
            subTotal += cartOrder.itemPrice!!
        }
        if (subTotal == 0.00) {
            setAmountDetails(subTotal, 0.0)
        } else {
            setAmountDetails(subTotal, 15.0)
        }
        view.findViewById<View>(R.id.btn_checkout).setOnClickListener {
            if(subTotal != 0.00){
                startActivity(Intent(context, AddressActivity::class.java))
            }
        }
    }

    fun removeItem(position: Int) {
        cartOrdersList.removeAt(position)
        cartOrdersQuantity.removeAt(position)
        ordersRecyclerView.adapter =
            CartOrderDetailsAdapter(this@CartFragment, cartOrdersList, cartOrdersQuantity)
    }

    fun setAmountDetails(subtotal: Double, shippingCharge: Double) {
        textOrderSubtotalTextView.text = "$${String.format("%.2f", Math.abs(subtotal))}"
        textChargeTextView.text = "$${shippingCharge}"
        textTotalAmountTextView.text =
            "$${String.format("%.2f", Math.abs(subtotal) + shippingCharge)}"
    }

    fun getSubtotal(): Double {
        return subTotal
    }

    fun setSubtotal(subTotal: Double) {
        this.subTotal = subTotal
    }
}