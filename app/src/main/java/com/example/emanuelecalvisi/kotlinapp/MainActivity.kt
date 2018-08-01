package com.example.emanuelecalvisi.kotlinapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.example.emanuelecalvisi.kotlinapp.Fragments.MainFragment
import com.example.emanuelecalvisi.kotlinapp.Utils.ActivityUtil
import android.content.DialogInterface
import android.support.v7.app.AlertDialog



class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val frag = MainFragment()
    ActivityUtil.setFragmentManager(supportFragmentManager)
    ActivityUtil.addFragmentToActivity( frag)
  }

  override fun onBackPressed() {

    val count = supportFragmentManager.backStackEntryCount

    if (count == 1) {

      AlertDialog.Builder(this)
          .setIcon(android.R.drawable.ic_dialog_alert)
          .setTitle("Closing App")
          .setMessage("Are you sure you want to close the app?")
          .setPositiveButton("Yes",
              DialogInterface.OnClickListener { dialog, which -> finish() })
          .setNegativeButton("No", null)
          .show()

    } else {
      supportFragmentManager.popBackStack()
    }
  }
}
