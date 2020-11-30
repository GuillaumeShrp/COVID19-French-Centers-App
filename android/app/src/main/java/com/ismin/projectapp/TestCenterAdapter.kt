package com.ismin.projectapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class TestCenterAdapter(private val centers: ArrayList<CovidTestCenter>) : RecyclerView.Adapter<CentersViewHolder>() {
    private lateinit var eContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CentersViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.element_covid_test_center, parent, false)
        eContext = parent.context
        return CentersViewHolder(row)
    }

    override fun onBindViewHolder(holder: CentersViewHolder, position: Int) {
        val (_, name, address) = this.centers[position]

        holder.txvCenterName.text = name
        holder.txvCenterAddress.text = address
        holder.imvFav.setOnClickListener( Listener(position,centers[position],false,holder.imvFav))
    }

    override fun getItemCount(): Int {
        return this.centers.size
    }

    fun updateItem(centersToDisplay: List<CovidTestCenter>) {
        centers.clear();
        centers.addAll(centersToDisplay)
        notifyDataSetChanged();
    }

    internal class Listener(
        position: Int,
        Data: CovidTestCenter,
        isFavourite: Boolean,
        favoriteView: ImageButton
    ) : View.OnClickListener {

        var favoriteButton: ImageButton
        var position: Int
        var isFavorite: Boolean
        override fun onClick(view: View?) {
            if (isFavorite) {
                favoriteButton.setImageResource(android.R.drawable.btn_star_big_off)
            } else {
                favoriteButton.setImageResource(android.R.drawable.btn_star_big_on)
            }
            isFavorite = !isFavorite
        }

        init {
            this.favoriteButton = favoriteView
            this.position = position
            this.isFavorite = isFavourite
        }
    }

}

