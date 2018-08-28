package com.example.emanuelecalvisi.kotlinapp.Fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emanuelecalvisi.kotlinapp.R
import okhttp3.OkHttpClient
import okhttp3.HttpUrl
import okhttp3.Request
import android.os.StrictMode
import kotlinx.android.synthetic.main.fragment_top.view.textView

/*

Created by Emanuele Calvisi on 02/08/2018.

*/

class TopFragment: CustomFragment(){

  var httpClient: OkHttpClient? = null
  var fragmentView: View? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    fragmentView = inflater.inflate(R.layout.fragment_top, container, false)
    super.TAG = "TopFragment"

    initClient()


    val thread = MyThread(::makeSyncRequest, fragmentView)
    thread.start()
    fragmentView!!.textView.text = makeSyncRequest()

    return fragmentView
  }

  private fun initClient() {
    // should be a singleton
    if (httpClient==null) httpClient = OkHttpClient()

    //set policy
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)
  }

  fun makeSyncRequest () : String {

    val urlBuilder = HttpUrl.parse("https://www.google.it")!!.newBuilder()
    /*urlBuilder.addQueryParameter("v", "1.0")
    urlBuilder.addQueryParameter("q", "android")*/
    val url = urlBuilder.build().toString()
    val request = Request.Builder().url(url).build()

    //Synchronous Network call
    val response = httpClient?.newCall(request)?.execute()
    return ("RESPONSE: "+response?.body()?.string())
  }

  fun modifyUI(stringa : String){
    fragmentView!!.textView.text = stringa
  }
}

  class MyThread(funzione: () -> String, v: View?) : Thread(){

    val view: View? = v
    val func = funzione

    override fun run() {
      println("${Thread.currentThread()} has run.")

      Thread.sleep(1000)
      val string =  func()
      println(string)
    }
  }

