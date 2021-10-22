package com.example.shoppal.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppal.R
import com.example.shoppal.activities.OrderItemActivity
import com.example.shoppal.models.OrderDetail
import com.example.shoppal.utils.Constants
import java.text.SimpleDateFormat

class OrderItemsAdapter(
    /**
     * Stores reference of the context
     */
    private val context: Context,
    /**
     * Stores all the order details in orderDetailsArrayList
     */
    private val orderDetailsArrayList: ArrayList<OrderDetail>
) : RecyclerView.Adapter<OrderItemsAdapter.OrderItemsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_order_item, parent, false)
        return OrderItemsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderItemsViewHolder, position: Int) {
        //Shows date and time at which the order has been placed
        val simpleDateFormat = SimpleDateFormat("d MMM, yyyy hh:mm a")
        val orderDate: String = simpleDateFormat.format(orderDetailsArrayList[position].orderDate!!)
        //Sets order details on OrderItemsViewHolder
        holder.getOrderIdTextView().text = "Ordered at: $orderDate"
        holder.getOrderTotalCostTextView().text =
            orderDetailsArrayList[position].orderReceipt.itemTotalAmount
        //Adding on click listener on cardOrderItemCardView that starts OrderItemActivity
        holder.getCardOrderItemCardView().setOnClickListener {
            val intent = Intent(context, OrderItemActivity::class.java)
            intent.putExtra(Constants.ORDER_ITEM, orderDetailsArrayList[position].orderId)
            context.startActivity(intent)
        }
    }

    /**
     * Returns size of orderDetailsArrayList arraylist
     */
    override fun getItemCount(): Int {
        return orderDetailsArrayList.size
    }

    class OrderItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Initialises orderIdTextView that shows order id
         */
        private val orderIdTextView: TextView = itemView.findViewById(R.id.text_order_id)

        /**
         * Initialises orderTotalCostTextView that shows total cost of the order placed
         */
        private val orderTotalCostTextView: TextView =
            itemView.findViewById(R.id.text_order_total_cost)

        /**
         * Initialises cardOrderItemCardView that helps to start OrderItemActivity
         */
        private val cardOrderItemCardView: CardView = itemView.findViewById(R.id.card_order_item)

        /**
         * Returns reference of orderIdTextView
         */
        fun getOrderIdTextView(): TextView {
            return orderIdTextView
        }

        /**
         * Returns reference of orderTotalCostTextView
         */
        fun getOrderTotalCostTextView(): TextView {
            return orderTotalCostTextView
        }

        /**
         * Returns reference of cardOrderItemCardView
         */
        fun getCardOrderItemCardView(): CardView {
            return cardOrderItemCardView
        }
    }
}