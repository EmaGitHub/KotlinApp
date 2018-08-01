package com.example.emanuelecalvisi.kotlinapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emanuelecalvisi.kotlinapp.R

/*

Created by Emanuele Calvisi on 31/07/2018.

*/

class MainFragment : Fragment(){

  val TAG : String = "mainFragment"

  //@BindView(R.id.pageTitle) TextView title;

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {

    // Inflate the layout for this fragment
    val view = inflater?.inflate(R.layout.fragment_one, container, false)
    return view
  }

}