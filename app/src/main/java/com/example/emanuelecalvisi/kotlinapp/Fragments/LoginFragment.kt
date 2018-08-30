package com.example.emanuelecalvisi.kotlinapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emanuelecalvisi.kotlinapp.R
import com.example.emanuelecalvisi.kotlinapp.Utils.ActivityUtil
import kotlinx.android.synthetic.main.activity_login.view.loginAccessButton

/*

Created by Emanuele Calvisi on 30/08/2018.

*/

class LoginFragment : CustomFragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    val view = inflater?.inflate(R.layout.activity_login, container, false)

    super.TAG = "LoginFragment"

    view.loginAccessButton.setOnClickListener(){
      ActivityUtil.addFragmentToActivity(HomeFragment())
    }

    return view

  }

}