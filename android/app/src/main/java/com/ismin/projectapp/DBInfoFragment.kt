package com.ismin.projectapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment


class DBInfoFragment : Fragment() {

    var infoWebView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.fragment_d_b_info, container, false)
        infoWebView = rootView.findViewById(R.id.f_dbinfo_webtxt) as WebView
        infoWebView!!.loadData(getString(R.string.hello), "text/html", "UTF-8")

        return rootView;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CovidTestCenterFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
