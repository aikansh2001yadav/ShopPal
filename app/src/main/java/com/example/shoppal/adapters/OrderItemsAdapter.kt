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

class OrderItemsAdapter(
    private val context: Context,
    private val orderDetailsArrayList: ArrayList<OrderDetail>
) : RecyclerView.Adapter<OrderItemsAdapter.OrderItemsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_order_item, parent, false)
        return OrderItemsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderItemsViewHolder, position: Int) {
        holder.getOrderIdTextView().text = "My Order: " + orderDetailsArrayList[position].orderId
        holder.getOrderTotalCostTextView().text =
            orderDetailsArrayList[position].orderReceipt.itemTotalAmount
        holder.getCardOrderItemCardView().setOnClickListener {
            val intent = Intent(context, OrderItemActivity::class.java)
            intent.putExtra(Constants.ORDER_ITEM, orderDetailsArrayList[position].orderId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return orderDetailsArrayList.size
    }

    class OrderItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderIdTextView: TextView = itemView.findViewById(R.id.text_order_id)
        private val orderTotalCostTextView: TextView =
            itemView.findViewById(R.id.text_order_total_cost)
        private val cardOrderItemCardView: CardView = itemView.findViewById(R.id.card_order_item)
        fun getOrderIdTextView(): TextView {
            return orderIdTextView
        }

        fun getOrderTotalCostTextView(): TextView {
            return orderTotalCostTextView
        }

        fun getCardOrderItemCardView(): CardView {
            return cardOrderItemCardView
        }
    }
}