package com.example.emanuelecalvisi.kotlinapp.Fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emanuelecalvisi.kotlinapp.R
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_one.sample_button
import kotlinx.android.synthetic.main.fragment_one.view.sample_button
import kotlinx.android.synthetic.main.fragment_two.view.sample_button_2

/*

Created by Emanuele Calvisi on 31/07/2018.

*/

class TopFragment : CustomFragment(){

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {

    val view = inflater?.inflate(R.layout.fragment_two, container, false)

    super.TAG = "TopFragment"

    view.sample_button_2.setOnClickListener{
      view.sample_button_2.setBackgroundColor(Color.BLUE)
      view.sample_button_2.setTextColor(Color.WHITE)
    }

    return view
  }

}