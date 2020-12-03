package com.ismin.projectapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailActivity : AppCompatActivity() {

    private var covidCenterTestRs = ""
    private lateinit var covidTestCenterService: CovidTestCenterService
    private lateinit var covidCenter: CovidTestCenter
    private lateinit var id: String
    private lateinit var imvFav: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        imvFav = findViewById(R.id.a_detail_btn_favorite)

        Log.e("info" ,"Lancement activité")

        this.covidCenterTestRs = intent.getStringExtra(Intent.EXTRA_TEXT).toString()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SERVER_BASE_URL)
            .build()

        covidTestCenterService = retrofit.create(CovidTestCenterService::class.java)

        covidTestCenterService.getCovidCenterByRs(covidCenterTestRs).enqueue {
            onResponse = { response ->
                covidCenter = response.body()!!
                favoriteSettings(covidCenter.ID)
                findViewById<TextView>(R.id.detail_rs).text = covidCenter.rs
                findViewById<TextView>(R.id.detail_addr).text = covidCenter.adresse
                findViewById<TextView>(R.id.detail_public).text = covidCenter.public
                findViewById<TextView>(R.id.detail_tel_rdv).text = covidCenter.tel_rdv
                if (covidCenter.do_prel == "OUI")
                    findViewById<ImageView>(R.id.detail_do_prel).setImageResource(android.R.drawable.presence_online)
                else
                    findViewById<ImageView>(R.id.detail_do_prel).setImageResource(android.R.drawable.presence_offline)
                if (covidCenter.do_antigenic == "OUI")
                    findViewById<ImageView>(R.id.detail_do_antigenic).setImageResource(android.R.drawable.presence_online)
                else
                    findViewById<ImageView>(R.id.detail_do_antigenic).setImageResource(android.R.drawable.presence_offline)
            }
            onFailure = {
                if (it != null) {
                    Log.e("info" ,"ERROR RECEPTION DATA")
                    //displayErrorToast(it)
                }
            }
        }
    }

    private fun favoriteSettings(id: String) {
        setFavorite(isFavorite(id))
        imvFav.setOnClickListener {
            var isFavoriteVal = isFavorite(id)
            if (!isFavoriteVal) {
                imvFav.setImageResource(android.R.drawable.btn_star_big_on)
                flipFavoriteValue(id, "add")
            } else {
                imvFav.setImageResource(android.R.drawable.btn_star_big_off)
                flipFavoriteValue(id, "remove")
            }
        }
    }

    private fun flipFavoriteValue(
        id: String,
        mode : String
    ) {
        val prefs: SharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val favoriteList = prefs.getStringSet(SHARED_FAVORITE_LIST, setOf<String>()) as MutableSet<String>
        if (mode == "add") {
            favoriteList.add(id)
        } else if (mode == "remove") {
            favoriteList.remove(id)
        }
        /** Push favorite update list in shared preferences */
        prefs.edit().putStringSet(SHARED_FAVORITE_LIST, favoriteList)
    }

    private fun isFavorite(id: String): Boolean {
        val prefs: SharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val favoriteList = prefs.getStringSet(SHARED_FAVORITE_LIST, setOf<String>()) as MutableSet<String>
        favoriteList.forEach {
            if (it == id)
                return true
        }
        return false
    }

    fun setFavorite(answer: Boolean){
        /** Set saved state favorite img*/
        if (answer) {
            imvFav.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            imvFav.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    fun backToCenterList(view: View) {
        val intent = Intent()
        intent.putExtra(FROM_DETAIL_EXTRA_KEY, true)
        setResult(RESULT_OK, intent)
        finish()
    }
}