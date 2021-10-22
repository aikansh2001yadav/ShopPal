package com.example.shoppal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
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
     * Stores reference of progress bar that shows progress
     */
    private var ordersProgressBar: ProgressBar? = null

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

        ordersProgressBar = view.findViewById(R.id.fragment_orders_progress)
        //Initialising user id of the current user
        val currentUserId = Firebase(requireActivity() as AppCompatActivity).currentUserId()
        orderStatusRecyclerView = view.findViewById(R.id.recyclerview_order_status)
        orderStatusRecyclerView.layoutManager = LinearLayoutManager(context)
        //Shows progress bar
        ordersProgressBar!!.visibility = View.VISIBLE
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
        //Hides progress bar
        ordersProgressBar!!.visibility = View.GONE
    }
}