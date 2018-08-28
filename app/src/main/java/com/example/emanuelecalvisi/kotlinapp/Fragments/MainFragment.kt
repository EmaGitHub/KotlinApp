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

class MainFragment : CustomFragment(){

  var visible: Boolean = false

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {

    val view = inflater?.inflate(R.layout.fragment_one, container, false)

    super.TAG = "MainFragment"

    view.kotlinImage.setOnClickListener{
      if(!visible) {
        view.fragmentTitle.setBackgroundColor(Color.BLACK)
        view.fragmentTitle.setTextColor(Color.WHITE)
        view.hideButton.visibility = View.VISIBLE;
        this.visible = true
      }
      else{
        view.fragmentTitle.setBackgroundColor(getColor(getResources(), R.color.cyan, null))
        view.fragmentTitle.setTextColor(Color.BLACK)
        view.hideButton.visibility = View.INVISIBLE;
        this.visible = false
      }
    }

    view.sample_button.setOnClickListener{
      ActivityUtil.addFragmentToActivity(HttpFragment())
    }

    view.hideButton.setOnClickListener(){
        ActivityUtil.addFragmentToActivity(TopFragment())
    }

    view.asyncButton.setOnClickListener(){
      ActivityUtil.addFragmentToActivity(AsyncFragment())
    }

    return view
  }

}