package com.ismin.projectapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
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
    private lateinit var covidTestCenterService: CovidTestCenterService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchNDisplayData()
    }

    private fun fetchNDisplayData() {
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


    /*private fun displayList() {
        val centerListFragment = CovidTestCenterFragment.newInstance(testCenterList)

        supportFragmentManager.beginTransaction()
            .replace(R.id.a_main_fragment_container, centerListFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }*/

    /*
    private fun displayDBInfo() {
        val dbInfoFragment = DBInfoFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .replace(R.id.a_main_fragment_container, dbInfoFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }*/

    private fun displayDBInfo() {
        val dbInfoFragment = DBInfoFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.a_main_fragment_container, dbInfoFragment)
            //.addToBackStack("dbInfoFragment")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }


    private fun displayList() {
        val centerListFragment = CovidTestCenterFragment.newInstance(testCenterList)

        supportFragmentManager.beginTransaction()
            .replace(R.id.a_main_fragment_container, centerListFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    fun actionToggleButton(view: View) {
        val toggle: ToggleButton = findViewById(R.id.a_main_button_list)
        toggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                displayDBInfo()
            } else {
                displayList()
            }
        }
    }

    /** POUR LE MENU DANS LA TOOLBAR */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) { //switch case
            R.id.menu_action_refresh -> {
                testCenterList.clear()
                fetchNDisplayData()
                Toast.makeText(this, "Bibliothèque effacée", Toast.LENGTH_SHORT).show()
                true //equal to return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    /*override fun backToCenterList(book: Book) {
        bookService.createBook(book).enqueue {
            onResponse = {
                bookshelf.addBook(it.body()!!)
                closeCreateFragment()
            }
            onFailure = {
                if (it != null) {
                    displayErrorToast(it)
                }
            }
        }
    }*/

    /*override fun closeCreateFragment() {
        displayList();

        a_main_btn_creation.visibility = View.VISIBLE
    }*/
}