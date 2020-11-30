package com.ismin.projectapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailActivity : AppCompatActivity() {

    private var covidCenterTestRs = ""
    private lateinit var covidTestCenterService: CovidTestCenterService
    private lateinit var covidCenter: CovidTestCenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

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
                findViewById<TextView>(R.id.detail_rs).text = covidCenter.rs
                findViewById<TextView>(R.id.detail_addr).text = covidCenter.adresse
                findViewById<TextView>(R.id.detail_do_antigenic).text = covidCenter.do_antigenic
                findViewById<TextView>(R.id.detail_do_prel).text = covidCenter.do_prel
                findViewById<TextView>(R.id.detail_public).text = covidCenter.public
                findViewById<TextView>(R.id.detail_tel_rdv).text = covidCenter.tel_rdv
            }
            onFailure = {
                if (it != null) {
                    Log.e("info" ,"ERROR RECEPTION DATA")
                    //displayErrorToast(it)
                }
            }
        }
    }
}