package com.ismin.projectapp

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.FragmentTransaction
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.widget.TextView

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val SERVER_BASE_URL = "https://covidtesingcenter-app.cleverapps.io/"
const val PREF_NAME = "SHARED_PREF_FILE"
const val SHARED_FAVORITE_LIST = "SHARED_FAVORITE_LIST"
const val BECOME_FAVORITE_EXTRA_KEY = "BECOME_FAVORITE_EXTRA_KEY"

class MainActivity : AppCompatActivity() {

    private var testCenterList: ArrayList<CovidTestCenter> = arrayListOf<CovidTestCenter>()
    private lateinit var covidTestCenterService: CovidTestCenterService
    private val detailActivityRequestCode = 1
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchData()

        //créaction de la liste des favorits
        prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putStringSet(SHARED_FAVORITE_LIST, mutableSetOf<String>()).apply()

        displayList()
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

    fun actionToggleButton(view: View) {
        val toggle: ToggleButton = view.findViewById(R.id.a_main_button_list)
        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                displayDBInfo()
            } else {
                displayList()
            }
        }
    }

    fun showDetailCovidCenter(view: View) {
        displayDBInfo()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT,  view.findViewById<TextView>(R.id.e_center_list_rs).text.toString())
        startActivity(intent)
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.detailActivityRequestCode) {
            val becomeFavorite = data?.extras?.get(BECOME_FAVORITE_EXTRA_KEY)
            flipFavoriteValue(id, favoriteList, "add")
        }
    }*/

    fun flipFavoriteValue(
        id: String,
        favoriteList: MutableSet<String>,
        mode : String
    ) {
        val prefs: SharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (mode == "add") {
            favoriteList.add(id)
        } else if (mode == "remove") {
            favoriteList.remove(id)
        }
        /** Push favorite update list in shared preferences */
        prefs.edit().putStringSet(SHARED_FAVORITE_LIST, favoriteList)
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