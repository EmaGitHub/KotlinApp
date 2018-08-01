package com.example.emanuelecalvisi.kotlinapp.Utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.example.emanuelecalvisi.kotlinapp.R

/*

Created by Emanuele Calvisi on 01/08/2018.

*/

object ActivityUtil {

  var manager: FragmentManager? = null

  fun addFragmentToActivity(fragment: Fragment) {

    val transaction =  manager!!.beginTransaction()
    transaction.add(R.id.fragmentContainer, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }

  fun setFragmentManager(manager: FragmentManager){
    this.manager = manager
  }

}