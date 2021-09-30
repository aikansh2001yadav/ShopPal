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
import java.util.*
import kotlin.collections.ArrayList


class CartFragment : Fragment() {

    private var subTotal: Double = 0.00
    private lateinit var cartDao: CartDao
    private lateinit var currentUserId: String
    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var textOrderSubtotalTextView: TextView
    private lateinit var textChargeTextView: TextView
    private lateinit var textTotalAmountTextView: TextView
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

        currentUserId = Firebase(requireActivity()).currentUserId()
        cartDao = Room.databaseBuilder(
            requireContext(),
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().cartDao()
        textOrderSubtotalTextView = view.findViewById(R.id.text_order_subtotal)
        textChargeTextView = view.findViewById(R.id.text_charge)
        textTotalAmountTextView = view.findViewById(R.id.text_total_amount)

        view.findViewById<View>(R.id.btn_checkout).setOnClickListener {
            if (subTotal != 0.00) {
                startActivity(Intent(context, SelectAddressActivity::class.java))
            }
        }
    }

    fun removeItem(position: Int) {
        cartOrdersList.removeAt(position)
        ordersRecyclerView.adapter =
            CartOrderDetailsAdapter(this@CartFragment, cartOrdersList)
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

    override fun onResume() {
        super.onResume()
        cartOrdersList = cartDao.getAllOrders(currentUserId) as ArrayList<CartOrder>
        ordersRecyclerView.apply {
            this.adapter = CartOrderDetailsAdapter(
                this@CartFragment, cartOrdersList
            )
            this.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
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