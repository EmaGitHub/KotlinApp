package com.example.emanuelecalvisi.kotlinapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emanuelecalvisi.kotlinapp.R

/*

Created by Emanuele Calvisi on 30/08/2018.

*/

class HomeFragment : CustomFragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    val view = inflater?.inflate(R.layout.home_fragment, container, false)

    super.TAG = "HomeFragment"


    return view

  }

}