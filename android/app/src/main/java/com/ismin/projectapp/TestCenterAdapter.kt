package com.ismin.projectapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TestCenterAdapter(private val centers: ArrayList<CovidTestCenter>) : RecyclerView.Adapter<CentersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CentersViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.element_covid_test_center, parent, false)
        return CentersViewHolder(row)
    }

    override fun onBindViewHolder(holder: CentersViewHolder, position: Int) {
        val (id, name, address) = this.centers[position]

        holder.txvCenterName.text = name
        holder.txvCenterAddress.text = address
    }

    override fun getItemCount(): Int {
        return this.centers.size
    }

    fun updateItem(centersToDisplay: List<CovidTestCenter>) {
        centers.clear();
        centers.addAll(centersToDisplay)
        notifyDataSetChanged();
    }
}

