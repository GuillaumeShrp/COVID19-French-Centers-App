package com.ismin.projectapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView



class TestCenterAdapter(
    private val centers: ArrayList<CovidTestCenter>
) : RecyclerView.Adapter<CentersViewHolder>() {

    private lateinit var eContext: Context
    private lateinit var favoriteList: ArrayList<Boolean>
    //private val PREF_NAME = "SHARED_PREF_FILE"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CentersViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(R.layout.element_covid_test_center, parent, false)
        eContext = parent.context

        /** Pull favorite list from shared preferences */
        var prefs: SharedPreferences =
            parent.context.getSharedPreferences("SHARED_PREF_FILE", Context.MODE_PRIVATE)
        favoriteList = prefs.getString(
            SHARED_FAVORITE_LIST,
            ObjectSerializer.serialize(ArrayList<Boolean>()
            ))?.let { ObjectSerializer.deserialize<ArrayList<Boolean>>(it) }!!

        return CentersViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: CentersViewHolder, position: Int) {
        val (_, name, address) = centers[position]

        holder.txvCenterName.text = name
        holder.txvCenterAddress.text = address

        holder.setFavorite(favoriteList[position])


        /** Action to make a favorite */
        holder.imvFav.setOnClickListener {

            Toast.makeText(eContext, "Total items : ${ this.centers.size}", Toast.LENGTH_SHORT).show()
            Toast.makeText(eContext, "fav start ${favoriteList[position]}", Toast.LENGTH_SHORT).show()
            if (favoriteList[position]) {
                holder.imvFav.setImageResource(android.R.drawable.btn_star_big_off)
            } else {
                holder.imvFav.setImageResource(android.R.drawable.btn_star_big_on)

            }
            favoriteList[position] = !favoriteList[position]


            /** Push favorite list in shared preferences */
            var prefs = eContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString(SHARED_FAVORITE_LIST, ObjectSerializer.serialize(favoriteList))


            Toast.makeText(eContext, "fav end ${favoriteList[position]}", Toast.LENGTH_SHORT).show()
        }
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

