package com.ismin.projectapp

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class TestCenterAdapter(
    private val centers: ArrayList<CovidTestCenter>,
    private val favoriteList: ArrayList<Boolean>
) : RecyclerView.Adapter<CentersViewHolder>() {

    private lateinit var eContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CentersViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.element_covid_test_center, parent, false)
        eContext = parent.context
        return CentersViewHolder(row)
    }

    override fun onBindViewHolder(holder: CentersViewHolder, position: Int) {
        val (_, name, address) = centers[position]

        holder.txvCenterName.text = name
        holder.txvCenterAddress.text = address
        holder.imvFav.setOnClickListener(ListenerFavorite(position,favoriteList[position],holder.imvFav,eContext))
    }

    override fun getItemCount(): Int {
        return this.centers.size
    }

    fun updateItem(centersToDisplay: List<CovidTestCenter>) {
        centers.clear();
        centers.addAll(centersToDisplay)
        notifyDataSetChanged();
    }

    /** A FAIRE
     * changer la valeur du boulean depuis activité main avec un onclick
     * faire un update de l'affichage en meme temps
     */

    internal class ListenerFavorite(
        position: Int,
        isFavourite: Boolean,
        favoriteView: ImageButton,
        eContext: Context
    ) : View.OnClickListener {

        private var favoriteButton: ImageButton = favoriteView
        private var isFavorite: Boolean = isFavourite
        private var eContext: Context = eContext
        private var position: Int = position

        override fun onClick(view: View?) {

            Toast.makeText(eContext, "Element position : $position", Toast.LENGTH_SHORT).show()

            if (isFavorite) {
                favoriteButton.setImageResource(android.R.drawable.btn_star_big_off)
            } else {
                favoriteButton.setImageResource(android.R.drawable.btn_star_big_on)
            }
            isFavorite = !isFavorite
        }
    }

}

