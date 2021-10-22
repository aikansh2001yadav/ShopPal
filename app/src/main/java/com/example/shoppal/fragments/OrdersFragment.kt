package com.example.shoppal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppal.R
import com.example.shoppal.adapters.OrderItemsAdapter
import com.example.shoppal.firebase.Firebase
import com.example.shoppal.firebase.OrderItemsDatabase
import com.example.shoppal.models.OrderDetail

class OrdersFragment : Fragment() {

    /**
     * Stores the reference of recyclerview that shows order status
     */
    private lateinit var orderStatusRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialising user id of the current user
        val currentUserId = Firebase(requireActivity()).currentUserId()
        orderStatusRecyclerView = view.findViewById(R.id.recyclerview_order_status)
        orderStatusRecyclerView.layoutManager = LinearLayoutManager(context)
        //Getting all orders from OrderItemDatabase
        OrderItemsDatabase(this@OrdersFragment).readOrders(currentUserId)
    }

    /**
     * Updates UI
     */
    fun updateUI(orderDetailList: ArrayList<OrderDetail>) {
        if (isAdded) {
            orderStatusRecyclerView.adapter = OrderItemsAdapter(requireContext(), orderDetailList)
        }
    }
}