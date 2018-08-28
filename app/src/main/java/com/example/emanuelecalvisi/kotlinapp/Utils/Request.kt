package com.example.emanuelecalvisi.kotlinapp.Utils

import java.net.URL

/*

Created by Emanuele Calvisi on 28/08/2018.

*/

class Request(private val url: String) {

  fun run(){
    val forecastJsonString = URL(url).readText()

  }
}