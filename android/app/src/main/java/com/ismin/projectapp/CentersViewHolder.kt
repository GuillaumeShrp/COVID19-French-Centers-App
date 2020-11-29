package com.ismin.projectapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CentersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txvCenterName = itemView.findViewById<TextView>(R.id.e_center_list_rs)
    var txvCenterAddress: TextView = itemView.findViewById(R.id.e_center_list_address)
}