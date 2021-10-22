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
import com.example.shoppal.room.databases.RoomDatabase
import com.example.shoppal.room.entities.CartOrder

class CartOrderDetailsAdapter(
    /**
     * Stores reference of CartFragment to update UI of CartFragment
     */
    private val fragment: CartFragment,
    /**
     * Stores all cart orders list in cartOrdersList
     */
    private val cartOrdersList: ArrayList<CartOrder>
) : RecyclerView.Adapter<CartOrderDetailsAdapter.CardOrderDetailsViewHolder>() {

    //Initialising cart dao that performs actions on cart products
    private val cartDao = Room.databaseBuilder(
        fragment.requireContext(),
        RoomDatabase::class.java,
        "offline_temp_database"
    ).allowMainThreadQueries().build().cartDao()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardOrderDetailsViewHolder {
        val view =
            LayoutInflater.from(fragment.context)
                .inflate(R.layout.layout_card_order_item, parent, false)
        return CardOrderDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardOrderDetailsViewHolder, position: Int) {
        //Showing image of cart order on OrderImageView
        Glide.with(fragment).load(cartOrdersList[position].itemImageUrl)
            .into(holder.getOrderImageView())
        //Shows all order details
        holder.getOrderNameTextView().text = cartOrdersList[position].itemName
        holder.getOrderPriceTextView().text = cartOrdersList[position].itemPrice.toString()
        holder.getCountTextView().text = cartOrdersList[position].itemCount.toString()

        //Adding on click listener on delete button on CardOrderDetailsViewHolder
        holder.getDeleteButton().setOnClickListener {
            var subTotal = fragment.getSubtotal()
            subTotal -= cartOrdersList[position].itemCount * cartOrdersList[position].itemPrice!!
            if(cartOrdersList.size == 1){
                fragment.setAmountDetails(subTotal, 0.0)
            }else{
                fragment.setAmountDetails(subTotal, 15.0)
            }
            fragment.setSubtotal(subTotal)
            //Deletes current order and update UI
            deleteCurrentOrder(position)
        }
        //Adding on click listener on sum button on CardOrderDetailsViewHolder
        holder.getSumButton().setOnClickListener {
            //Increases no of cart order
            cartOrdersList[position].itemCount++
            holder.getCountTextView().text = cartOrdersList[position].itemCount.toString()
            var subTotal = fragment.getSubtotal()
            subTotal += cartOrdersList[position].itemPrice!!
            fragment.setAmountDetails(subTotal, 15.0)
            fragment.setSubtotal(subTotal)
            //Inserts cart order in room database
            cartDao.insertOrder(cartOrdersList[position])
        }
        //Adding on click listener on subtract button on CartOrderDetailsViewHolder
        holder.getSubtractButton().setOnClickListener {
            //Decrease no of cart order
            var subTotal = fragment.getSubtotal()
            subTotal -= cartOrdersList[position].itemPrice!!
            if (cartOrdersList[position].itemCount == 1) {
                if (cartOrdersList.size == 1) {
                    fragment.setAmountDetails(subTotal, 0.0)
                } else {
                    fragment.setAmountDetails(subTotal, 15.0)
                }
                //Deletes cart order from room database and update UI
                deleteCurrentOrder(position)
            } else {
                //Decreases no of cart orders
                cartOrdersList[position].itemCount--
                holder.getCountTextView().text = cartOrdersList[position].itemCount.toString()
                fragment.setAmountDetails(subTotal, 15.0)
                cartDao.insertOrder(cartOrdersList[position])
            }
            //Update UI
            fragment.setSubtotal(subTotal)
        }
    }

    /**
     * Deletes current order from cart dao in room database
     */
    private fun deleteCurrentOrder(position: Int) {
        //Deletes current order from room database
        cartDao.deleteCurrentOrder(
            cartOrdersList[position].orderId,
            cartOrdersList[position].currentUserId.toString()
        )
        //Updates UI
        fragment.removeItem(position)
    }

    /**
     * Returns size of cart orders list
     */
    override fun getItemCount(): Int {
        return cartOrdersList.size
    }

    class CardOrderDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Initialises orderImageView that shows image of the cart order
         */
        private val orderImageView = itemView.findViewById<ImageView>(R.id.order_imageview)

        /**
         * Initialises orderNameTextView that shows name of the order
         */
        private val orderNameTextView = itemView.findViewById<TextView>(R.id.text_order_name)

        /**
         * Initialises orderPriceTextView that shows price of the order
         */
        private val orderPriceTextView = itemView.findViewById<TextView>(R.id.text_order_price)

        /**
         * Initialises subtractButton that decreases no of cart order to be bought
         */
        private val subtractButton = itemView.findViewById<ImageButton>(R.id.btn_subtract)

        /**
         * Initialises countTextView that shows no of items to be bought
         */
        private val countTextView = itemView.findViewById<TextView>(R.id.text_count)

        /**
         * Initialises sumButton that increases no of cart order to be bought
         */
        private val sumButton = itemView.findViewById<ImageButton>(R.id.btn_sum)

        /**
         * Initialises deleteButton that removes order from the cart
         */
        private val deleteButton = itemView.findViewById<ImageButton>(R.id.btn_delete)

        /**
         * Returns reference of the orderImageView
         */
        fun getOrderImageView(): ImageView {
            return orderImageView
        }

        /**
         * Returns reference of the orderNameTextView
         */
        fun getOrderNameTextView(): TextView {
            return orderNameTextView
        }

        /**
         * Returns reference of the orderPriceTextView
         */
        fun getOrderPriceTextView(): TextView {
            return orderPriceTextView
        }

        /**
         * Returns reference of subtractButton
         */
        fun getSubtractButton(): ImageButton {
            return subtractButton
        }

        /**
         * Returns reference of countTextView
         */
        fun getCountTextView(): TextView {
            return countTextView
        }

        /**
         * Returns reference of sumButton
         */
        fun getSumButton(): ImageButton {
            return sumButton
        }

        /**
         * Returns reference of deleteButton
         */
        fun getDeleteButton(): ImageButton {
            return deleteButton
        }
    }
}