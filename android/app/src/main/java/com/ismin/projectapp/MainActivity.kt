package com.ismin.projectapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.FragmentTransaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val SERVER_BASE_URL = "https://covidtesingcenter-app.cleverapps.io/"

class MainActivity : AppCompatActivity() {

    private var testCenterList: ArrayList<CovidTestCenter> = arrayListOf<CovidTestCenter>()
    private var testCenterFavorite: ArrayList<Boolean> = arrayListOf<Boolean>()
    private lateinit var covidTestCenterService: CovidTestCenterService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchData()
    }

    private fun fetchData() {
        //Récupération des données sur le serveur web
        Log.i("INFO", "Récupération des données")

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SERVER_BASE_URL)
            .build()

        covidTestCenterService = retrofit.create(CovidTestCenterService::class.java)

        covidTestCenterService.getAllCovidCenters().enqueue {
            onResponse = { response ->
                val allCovidCenter = response.body()
                allCovidCenter?.forEach { covidCenter ->
                    testCenterList.add(covidCenter)
                    testCenterFavorite.add(false)
                }
                displayList()
            }

            onFailure = {
                if (it != null) {
                    displayErrorToast(it)
                }
            }
        }
    }

    private fun displayErrorToast(t: Throwable) {
        Toast.makeText(
            applicationContext,
            "Network error ${t.localizedMessage}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun displayDBInfo() {
        val dbInfoFragment = DBInfoFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.a_main_fragment_container, dbInfoFragment)
            //.addToBackStack("dbInfoFragment")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()

        Toast.makeText(this, "Display DB info fragment", Toast.LENGTH_SHORT).show()
    }


    private fun displayList() {
        val centerListFragment = CovidTestCenterFragment.newInstance(testCenterList, testCenterFavorite)

        supportFragmentManager.beginTransaction()
            .replace(R.id.a_main_fragment_container, centerListFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()

        Toast.makeText(this, "Display center list fragment", Toast.LENGTH_SHORT).show()
    }

    fun actionToggleButton(view: View) {
        //Toast.makeText(this, "Toggle action", Toast.LENGTH_SHORT).show()
        val toggle: ToggleButton = view.findViewById(R.id.a_main_button_list)
        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                displayDBInfo()
            } else {
                displayList()
            }
        }
    }

    /**
    fun favoriteButton(view: View) {
        il faut la postion du book holder pour changer le bool dans la liste
        donc se placzer avec un listener dans le holder

    }*/

    /** POUR LE MENU DE LA TOOLBAR */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) { //switch case
            R.id.menu_action_refresh -> {
                testCenterList.clear()
                fetchData()
                Toast.makeText(this, "Server data refreshed", Toast.LENGTH_SHORT).show()
                true //equal to return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}