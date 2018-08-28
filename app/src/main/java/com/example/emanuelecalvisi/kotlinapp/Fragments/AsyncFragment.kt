package com.example.emanuelecalvisi.kotlinapp.Fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.support.v4.content.res.ResourcesCompat.getColor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emanuelecalvisi.kotlinapp.R
import android.widget.Toast
import com.example.emanuelecalvisi.kotlinapp.Utils.ActivityUtil
import kotlinx.android.synthetic.main.fragment_one.view.*
import java.time.Duration

/*

Created by Emanuele Calvisi on 31/07/2018.

*/

class AsyncFragment : CustomFragment(){

  var visible: Boolean = false

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {

    val view = inflater?.inflate(R.layout.fragment_async, container, false)

    super.TAG = "AsyncFragment"

    return view
  }

}