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

    private var covidCenterTestId = ""
    //private lateinit var covidTestCenterService: CovidTestCenterService
    private var covidCenter: CovidTestCenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        Log.e("info" ,"Lancement activité")
        /*
        this.covidCenterTestId = intent.getStringExtra(Intent.EXTRA_TEXT).toString()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SERVER_BASE_URL)
            .build()

        covidTestCenterService = retrofit.create(CovidTestCenterService::class.java)

        covidTestCenterService.getCovidCenter(covidCenterTestId).enqueue {
            onResponse = { response ->
                Log.e("info" ,response.body().toString())
                covidCenter = response.body()!!
            }
            onFailure = {
                if (it != null) {
                    Log.e("info" ,"ERROR RECEPTION DATA")
                    //displayErrorToast(it)
                }
            }
        }*/
        if (covidCenter != null)
        {
            findViewById<TextView>(R.id.detail_rs).text = covidCenter!!.rs
            findViewById<TextView>(R.id.detail_addr).text = covidCenter!!.adresse
            findViewById<TextView>(R.id.detail_do_antigenic).text = covidCenter!!.do_antigenic
            findViewById<TextView>(R.id.detail_do_prel).text = covidCenter!!.do_prel
            findViewById<TextView>(R.id.detail_public).text = covidCenter!!.public
            findViewById<TextView>(R.id.detail_tel_rdv).text = covidCenter!!.tel_rdv
        }

        else
        {
            findViewById<TextView>(R.id.detail_rs).text = "Error"
        }

    }
}