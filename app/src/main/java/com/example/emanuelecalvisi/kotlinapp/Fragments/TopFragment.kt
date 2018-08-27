package com.example.emanuelecalvisi.kotlinapp.Fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emanuelecalvisi.kotlinapp.R
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.HttpUrl
import okhttp3.Request

/*

Created by Emanuele Calvisi on 02/08/2018.

*/

class TopFragment: CustomFragment(){

  val httpClient: OkHttpClient? = null
  val picassoCLient: Picasso.Builder? = null
  var fragmentView: View? = null
  var myActivity: Activity = Activity()
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    fragmentView = inflater.inflate(R.layout.fragment_top, container, false)
    initClients()

    val thread = MyThread(myActivity)
    thread.start()

    return fragmentView
  }

  override fun onAttach(context: Context?) {

    super.onAttach(context)
    myActivity = context as Activity
  }


  private fun initClients() {

  }

  fun makeRequest () : Unit {

    // should be a singleton
    val client = OkHttpClient()

    val urlBuilder = HttpUrl.parse("https://www.google.it")!!.newBuilder()
    /*urlBuilder.addQueryParameter("v", "1.0")
    urlBuilder.addQueryParameter("q", "android")*/
    val url = urlBuilder.build().toString()

    val request = Request.Builder().url(url).build()

    //Synchronous Network call
    val response = client.newCall(request).execute()

  }
}

  class MyThread(activity: Activity) : Thread(){

    var a = activity

    override fun run() {
      println("${Thread.currentThread()} has run.")

      while (true) {

        Thread.sleep(200)
        a.runOnUiThread {


        }

      }
    }
  }

