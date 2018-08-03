package com.example.emanuelecalvisi.kotlinapp.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.emanuelecalvisi.kotlinapp.R
import kotlinx.android.synthetic.main.fragment_two.view.backButton
import kotlinx.android.synthetic.main.fragment_two.view.editTextWebView
import kotlinx.android.synthetic.main.fragment_two.view.forwardButton
import kotlinx.android.synthetic.main.fragment_two.view.search_address
import kotlinx.android.synthetic.main.fragment_two.view.webView

/*

Created by Emanuele Calvisi on 31/07/2018.

*/

class HttpFragment : CustomFragment(){

  var webAdresses: ArrayList<String> = arrayListOf()
  var tempWebAddress : String? = null
  var index: Int = 0
  var indexTop: Int = 0

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {

    val view = inflater?.inflate(R.layout.fragment_two, container, false)

    super.TAG = "HttpFragment"


    view.search_address.setOnClickListener{
      tempWebAddress = "https://"+view.editTextWebView.text
      webAdresses.add(tempWebAddress.toString())
      view.webView.loadUrl(tempWebAddress)
      indexTop = index + 1
      index++
    }

    view.backButton.setOnClickListener{
      if(index>1) {
        index--
        tempWebAddress = webAdresses.get(index - 1)
        view.webView.loadUrl(tempWebAddress)
      }
    }

    view.forwardButton.setOnClickListener{
      if(index<indexTop){
        tempWebAddress = webAdresses.get(index++)
        view.webView.loadUrl(tempWebAddress)
      }
    }

    view.webView.webViewClient = object : WebViewClient(){
      override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
          view?.loadUrl(url)
          return true
      }
    }

    return view
  }
}