package com.ismin.projectapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    //private val testCenterList = CovidTestCenter();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun displayErrorToast(t: Throwable) {
        Toast.makeText(
            applicationContext,
            "Network error ${t.localizedMessage}",
            Toast.LENGTH_LONG
        ).show()
    }


    /*private fun displayList() {
        val centerListFragment = CovidTestCenterFragment.newInstance(testCenterList.getAllBooks())

        supportFragmentManager.beginTransaction()
            .replace(R.id.a_main_fragment_container, centerListFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }*/

    private fun displayDBInfo() {
        val dbInfoFragment = DBInfoFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .replace(R.id.a_main_fragment_container, dbInfoFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    fun goToDBInfo(view: View) {
        val dbInfoFragment = DBInfoFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.a_main_fragment_container, dbInfoFragment)
            .addToBackStack("dbInfoFragment")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()

        //a_main_btn_creation.visibility = View.GONE
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