package com.example.emanuelecalvisi.kotlinapp.Utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.example.emanuelecalvisi.kotlinapp.Fragments.CustomFragment
import com.example.emanuelecalvisi.kotlinapp.R

/*

Created by Emanuele Calvisi on 01/08/2018.

*/

object ActivityUtil {

  var manager: FragmentManager? = null

  fun addFragmentToActivity(fragment: CustomFragment) {

    val transaction =  manager!!.beginTransaction()
    transaction.setCustomAnimations(R.anim.enter_from_top, R.anim.exit_to_top)
    transaction.add(R.id.fragmentContainer, fragment)
    transaction.addToBackStack(fragment.TAG)
    transaction.commit()
  }

  fun configure(manager: FragmentManager){
    this.manager = manager
  }

}