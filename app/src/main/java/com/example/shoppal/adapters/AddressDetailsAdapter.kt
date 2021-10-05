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
import com.example.shoppal.activities.SelectAddressActivity
import com.example.shoppal.room.entities.AddressDetail
import com.example.shoppal.utils.Constants

class AddressDetailsAdapter(
    private val context: Context,
    private val addressDetailsList: ArrayList<AddressDetail>,
    private val directBuyStatus:Boolean
) :
    RecyclerView.Adapter<AddressDetailsAdapter.AddressDetailsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressDetailsViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_address_item, parent, false)
        return AddressDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressDetailsViewHolder, position: Int) {
        holder.getFullNameTextView().text = addressDetailsList[position].addressFullName
        holder.getAddressTypeTextView().text = addressDetailsList[position].addressType
        holder.getFullAddressTextView().text =
            addressDetailsList[position].addressDetails + "," + addressDetailsList[position].addressPinCode
        holder.getPhoneTextView().text = addressDetailsList[position].addressPhone
        holder.getAddressCardView().setOnClickListener {
            val intent = Intent(context, CheckoutActivity::class.java)
            intent.putExtra(Constants.DIRECT_BUY_STATUS, directBuyStatus)
            intent.putExtra(Constants.ADDRESS_ID, addressDetailsList[position].addressId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return addressDetailsList.size
    }

    class AddressDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fullNameTextView: TextView = itemView.findViewById(R.id.text_full_name)
        private val addressTypeTextView: TextView = itemView.findViewById(R.id.text_address_type)
        private val fullAddressTextView: TextView = itemView.findViewById(R.id.text_full_address)
        private val phoneTextView: TextView = itemView.findViewById(R.id.text_phone)
        private val cardAddress: CardView = itemView.findViewById(R.id.card_address)

        fun getFullNameTextView(): TextView {
            return fullNameTextView
        }

        fun getAddressTypeTextView(): TextView {
            return addressTypeTextView
        }

        fun getFullAddressTextView(): TextView {
            return fullAddressTextView
        }

        fun getPhoneTextView(): TextView {
            return phoneTextView
        }

        fun getAddressCardView(): CardView {
            return cardAddress
        }
    }
}