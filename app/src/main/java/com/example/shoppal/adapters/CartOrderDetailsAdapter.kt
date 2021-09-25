package com.example.shoppal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.shoppal.R
import com.example.shoppal.fragments.CartFragment
import com.example.shoppal.room.databases.CartOrderDatabase
import com.example.shoppal.room.entities.CartOrder

class CartOrderDetailsAdapter(
    private val fragment: CartFragment,
    private val cartOrdersList: ArrayList<CartOrder>,
    private val cartOrdersQuantity: ArrayList<Int>
) : RecyclerView.Adapter<CartOrderDetailsAdapter.CardOrderDetailsViewHolder>() {

    private val cartDao = Room.databaseBuilder(
        fragment.requireContext(),
        CartOrderDatabase::class.java,
        "cart_database"
    ).allowMainThreadQueries().build().cartDao()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardOrderDetailsViewHolder {
        val view =
            LayoutInflater.from(fragment.context)
                .inflate(R.layout.layout_card_order_item, parent, false)
        return CardOrderDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardOrderDetailsViewHolder, position: Int) {
        Glide.with(fragment).load(cartOrdersList[position].itemImageUrl)
            .into(holder.getOrderImageView())
        holder.getOrderNameTextView().text = cartOrdersList[position].itemName
        holder.getOrderPriceTextView().text = cartOrdersList[position].itemPrice.toString()
        holder.getCountTextView().text = cartOrdersQuantity[position].toString()

        holder.getDeleteButton().setOnClickListener {
            var subTotal = fragment.getSubtotal()
            subTotal -= cartOrdersQuantity[position] * cartOrdersList[position].itemPrice!!
            if(cartOrdersList.size == 1){
                fragment.setAmountDetails(subTotal, 0.0)
            }else{
                fragment.setAmountDetails(subTotal, 15.0)
            }
            fragment.setSubtotal(subTotal)
            deleteCurrentOrder(position)
        }
        holder.getSumButton().setOnClickListener {
            cartOrdersQuantity[position]++
            holder.getCountTextView().text = cartOrdersQuantity[position].toString()
            var subTotal = fragment.getSubtotal()
            subTotal += cartOrdersList[position].itemPrice!!
            fragment.setAmountDetails(subTotal, 15.0)
            fragment.setSubtotal(subTotal)
        }
        holder.getSubtractButton().setOnClickListener {
            var subTotal = fragment.getSubtotal()
            subTotal -= cartOrdersList[position].itemPrice!!
            if (cartOrdersQuantity[position] == 1) {
                if (cartOrdersList.size == 1) {
                    fragment.setAmountDetails(subTotal, 0.0)
                } else {
                    fragment.setAmountDetails(subTotal, 15.0)
                }
                deleteCurrentOrder(position)
            } else {
                cartOrdersQuantity[position]--
                holder.getCountTextView().text = cartOrdersQuantity[position].toString()
                fragment.setAmountDetails(subTotal, 15.0)
            }
            fragment.setSubtotal(subTotal)
        }
    }

    private fun deleteCurrentOrder(position: Int) {
        cartDao.deleteCurrentOrder(
            cartOrdersList[position].orderId,
            cartOrdersList[position].currentUserId.toString()
        )
        fragment.removeItem(position)
    }

    override fun getItemCount(): Int {
        return cartOrdersList.size
    }

    class CardOrderDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderImageView = itemView.findViewById<ImageView>(R.id.order_imageview)
        private val orderNameTextView = itemView.findViewById<TextView>(R.id.text_order_name)
        private val orderPriceTextView = itemView.findViewById<TextView>(R.id.text_order_price)
        private val subtractButton = itemView.findViewById<ImageButton>(R.id.btn_subtract)
        private val countTextView = itemView.findViewById<TextView>(R.id.text_count)
        private val sumButton = itemView.findViewById<ImageButton>(R.id.btn_sum)
        private val deleteButton = itemView.findViewById<ImageButton>(R.id.btn_delete)

        fun getOrderImageView(): ImageView {
            return orderImageView
        }

        fun getOrderNameTextView(): TextView {
            return orderNameTextView
        }

        fun getOrderPriceTextView(): TextView {
            return orderPriceTextView
        }

        fun getSubtractButton(): ImageButton {
            return subtractButton
        }

        fun getCountTextView(): TextView {
            return countTextView
        }

        fun getSumButton(): ImageButton {
            return sumButton
        }

        fun getDeleteButton(): ImageButton {
            return deleteButton
        }
    }
}