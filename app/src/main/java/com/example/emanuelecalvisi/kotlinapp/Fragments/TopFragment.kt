package com.example.emanuelecalvisi.kotlinapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.emanuelecalvisi.kotlinapp.R
import kotlinx.android.synthetic.main.fragment_two.view.editTextWebView

import kotlinx.android.synthetic.main.fragment_two.view.sample_button_2
import kotlinx.android.synthetic.main.fragment_two.view.webView

/*

Created by Emanuele Calvisi on 31/07/2018.

*/

class TopFragment : CustomFragment(){

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {

    val view = inflater?.inflate(R.layout.fragment_two, container, false)

    super.TAG = "TopFragment"

    view.sample_button_2.setOnClickListener{
      view.webView.loadUrl("http://"+view.editTextWebView.text)
    }

    view.webView.webViewClient = object : WebViewClient(){
      override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
          view?.loadUrl(url)
          return true
      }
    }
    view.webView.loadUrl("https://www.google.it/")

    return view
  }

}