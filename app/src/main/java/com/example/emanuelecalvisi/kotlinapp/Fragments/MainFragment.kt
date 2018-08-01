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

/*

Created by Emanuele Calvisi on 31/07/2018.

*/

class MainFragment : Fragment(){

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {

    val view = inflater?.inflate(R.layout.fragment_one, container, false)

    view.sample_button.setOnClickListener{
      Toast.makeText(activity, "clicked ", Toast.LENGTH_LONG).show()
      sample_button.setBackgroundColor(Color.BLUE)
      sample_button.setTextColor(Color.WHITE)
    }

    return view
  }

}