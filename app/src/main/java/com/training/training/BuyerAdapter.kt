package com.training.training

import android.content.Context
import android.widget.ArrayAdapter
import android.R.attr.name
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



class BuyerAdapter(context: Context, data: List<Buyer>) : ArrayAdapter<Buyer>(context, 0, data) {
    val mContext = context
    val mData = data

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // Get the data item for this position
        val user = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.buyer_item, parent, false)
        }
        // Lookup view for data population
        val tvName = convertView!!.findViewById(R.id.tv_name) as TextView
        val tvAddress = convertView!!.findViewById(R.id.tv_address) as TextView

        // Populate the data into the template view using the data object
        tvName.text = mData[position].name

        tvAddress.text = mData[position].address
        // Return the completed view to render on screen
        return convertView
    }
}