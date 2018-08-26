package com.example.emanuelecalvisi.kotlinapp.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emanuelecalvisi.kotlinapp.R
import android.widget.Toast
import com.example.emanuelecalvisi.kotlinapp.Utils.ActivityUtil
import kotlinx.android.synthetic.main.fragment_one.view.*

/*

Created by Emanuele Calvisi on 31/07/2018.

*/

class MainFragment : CustomFragment(){

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {

    val view = inflater?.inflate(R.layout.fragment_one, container, false)

    super.TAG = "MainFragment"

    view.fragmentTitle.setOnClickListener{
      view.fragmentTitle.setBackgroundColor(Color.BLUE)
      view.fragmentTitle.setTextColor(Color.WHITE)
      view.hideButton.visibility = View.VISIBLE;
    }

    view.sample_button.setOnClickListener{
      ActivityUtil.addFragmentToActivity(HttpFragment())
    }

    view.hideButton.setOnClickListener(){
        ActivityUtil.addFragmentToActivity(TopFragment())
    }

    return view
  }

}