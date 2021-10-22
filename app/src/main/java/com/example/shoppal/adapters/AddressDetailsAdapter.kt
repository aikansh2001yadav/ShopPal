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
import com.example.shoppal.activities.CheckoutActivity
import com.example.shoppal.room.entities.AddressDetail
import com.example.shoppal.utils.Constants

class AddressDetailsAdapter(
    /**
     * Stores reference of the context
     */
    private val context: Context,
    /**
     * Stores all the address details list in the below array list
     */
    private val addressDetailsList: ArrayList<AddressDetail>,
    /**
     * Stores whether product is bought directly via ItemOverviewActivity
     */
    private val directBuyStatus:Boolean
) :
    RecyclerView.Adapter<AddressDetailsAdapter.AddressDetailsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressDetailsViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_address_item, parent, false)
        return AddressDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressDetailsViewHolder, position: Int) {
        //Sets address details in the AddressDetailsViewHolder
        holder.getFullNameTextView().text = addressDetailsList[position].addressFullName
        holder.getAddressTypeTextView().text = addressDetailsList[position].addressType
        holder.getFullAddressTextView().text =
            addressDetailsList[position].addressDetails + "," + addressDetailsList[position].addressPinCode
        holder.getPhoneTextView().text = addressDetailsList[position].addressPhone

        //Adding on click listener on the AddressCardView
        holder.getAddressCardView().setOnClickListener {
            val intent = Intent(context, CheckoutActivity::class.java)
            intent.putExtra(Constants.DIRECT_BUY_STATUS, directBuyStatus)
            intent.putExtra(Constants.ADDRESS_ID, addressDetailsList[position].addressId)
            context.startActivity(intent)
        }
    }

    /**
     * Returns size of the address details list
     */
    override fun getItemCount(): Int {
        return addressDetailsList.size
    }

    class AddressDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Initialises fullNameTextView that shows full name
         */
        private val fullNameTextView: TextView = itemView.findViewById(R.id.text_full_name)

        /**
         * Initialises addressTypeTextView that shows address type
         */
        private val addressTypeTextView: TextView = itemView.findViewById(R.id.text_address_type)

        /**
         * Initialises fullAddressTextView that shows full address
         */
        private val fullAddressTextView: TextView = itemView.findViewById(R.id.text_full_address)

        /**
         * Initialises phoneTextView that shows phone number
         */
        private val phoneTextView: TextView = itemView.findViewById(R.id.text_phone)

        /**
         * Initialises cardAddress that contains all address details
         */
        private val cardAddress: CardView = itemView.findViewById(R.id.card_address)

        /**
         * Returns the reference of fullNameTextView
         */
        fun getFullNameTextView(): TextView {
            return fullNameTextView
        }

        /**
         * Returns the reference of addressTypeTextView
         */
        fun getAddressTypeTextView(): TextView {
            return addressTypeTextView
        }

        /**
         * Returns the reference of fullAddressTextView
         */
        fun getFullAddressTextView(): TextView {
            return fullAddressTextView
        }

        /**
         * Returns the reference of phoneTextView
         */
        fun getPhoneTextView(): TextView {
            return phoneTextView
        }

        /**
         * Returns the reference of addressCardView
         */
        fun getAddressCardView(): CardView {
            return cardAddress
        }
    }
}