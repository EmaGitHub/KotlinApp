package com.example.emanuelecalvisi.kotlinapp.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
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
  var booleanCheck: Boolean = true

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {

    val view = inflater?.inflate(R.layout.fragment_two, container, false)
    val editText : EditText = view.editTextWebView

    super.TAG = "HttpFragment"


    view.search_address.setOnClickListener{
      tempWebAddress = ""+editText.text.toString()
      webAdresses.add(tempWebAddress.toString())
      view.webView.loadUrl(tempWebAddress)
      booleanCheck = false
      indexTop = index + 1
      index++
      view.editTextWebView.hideKeyboard()
    }

    view.backButton.setOnClickListener{
      if(index>1) {
        tempWebAddress = webAdresses.get(index - 1)
        editText.setText(tempWebAddress)
        view.webView.loadUrl(tempWebAddress)
        booleanCheck = false
        index--
      }
    }

    view.forwardButton.setOnClickListener{
      if(index<indexTop){
        tempWebAddress = webAdresses.get(index++)
        editText.setText(tempWebAddress)
        view.webView.loadUrl(tempWebAddress)
      }
    }

    view.webView.webViewClient = object : WebViewClient(){
      override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

          if(booleanCheck) {
              editText.setText(""+url)
              webAdresses.add("" + url)
              indexTop = index+1
              index++
          }
          view?.loadUrl(url)
          booleanCheck = true
          return true
      }
    }

    return view
  }

  fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
  }
}