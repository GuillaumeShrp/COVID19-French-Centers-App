package com.ismin.projectapp


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager


class DBInfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment





        val rootView = inflater.inflate(R.layout.fragment_d_b_info, container, false)


/*
        val view = WebView(rootView)
        view.isVerticalScrollBarEnabled = false
        (rootView.findViewById(R.id.f_dbinfo_webtxt) as LinearLayout).addView(view)
        view.loadData(getString(R.string.hello), "text/html; charset=utf-8", "utf-8")
*/

        return rootView;
    }
}