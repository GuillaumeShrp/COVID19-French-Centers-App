package com.ismin.projectapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val SERVER_BASE_URL = "https://covidtesingcenter-app.cleverapps.io/"
const val PREF_NAME = "SHARED_PREF_FILE"
const val SHARED_FAVORITE_LIST = "SHARED_FAVORITE_LIST"
const val FROM_DETAIL_EXTRA_KEY = "FROM_DETAIL_EXTRA_KEY"

class MainActivity : AppCompatActivity() {

    private var testCenterList: ArrayList<CovidTestCenter> = arrayListOf<CovidTestCenter>()
    private lateinit var covidTestCenterService: CovidTestCenterService
    private val detailActivityRequestCode = 1
    private lateinit var prefs: SharedPreferences
    private var togg: ToggleButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchData()

        //créaction de la liste des favorits
        val prefs: SharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        //prefs.edit().putStringSet(SHARED_FAVORITE_LIST, mutableSetOf<String>()).apply()
        Toast.makeText(applicationContext, "favList : ${prefs.getStringSet(SHARED_FAVORITE_LIST, setOf<String>())}", Toast.LENGTH_SHORT).show()

        /*
        Changing fragment with the button
        * */
        val toggle: ToggleButton = findViewById(R.id.a_main_button_list)
        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                displayDBInfo()
            } else {
                displayList()
            }
        }
    }

    private fun fetchData() {
        //Récupération des données sur le serveur web
        Log.i("INFO", "Récupération des données")

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SERVER_BASE_URL)
            .build()

        var i : Int = 0

        covidTestCenterService = retrofit.create(CovidTestCenterService::class.java)

        covidTestCenterService.getAllCovidCenters().enqueue {
            onResponse = { response ->
                val allCovidCenter = response.body()
                allCovidCenter?.forEach { covidCenter ->
                if (i < 15)
                {
                    i += 1
                    testCenterList.add(covidCenter)
                }
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
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
        //Toast.makeText(this, "Display DB info fragment", Toast.LENGTH_SHORT).show()
    }


    private fun displayList() {
        val centerListFragment = CovidTestCenterFragment.newInstance(testCenterList)
        supportFragmentManager.beginTransaction()
            .replace(R.id.a_main_fragment_container, centerListFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
        //Toast.makeText(this, "Display center list fragment", Toast.LENGTH_SHORT).show()
    }


    fun showDetailCovidCenter(view: View) {
        displayDBInfo()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT,  view.findViewById<TextView>(R.id.e_center_list_rs).text.toString())
        startActivityForResult(intent, detailActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.detailActivityRequestCode) {
            displayList()
        }
    }

    override fun onResume() {
        super.onResume()
        displayList()
    }

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
                Toast.makeText(this, "Server data refreshed\nFavorites reset", Toast.LENGTH_SHORT).show()
                true //equal to return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}