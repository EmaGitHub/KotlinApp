package com.example.emanuelecalvisi.kotlinapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.emanuelecalvisi.kotlinapp.Fragments.MainFragment
import com.example.emanuelecalvisi.kotlinapp.Utils.ActivityUtil
import android.content.DialogInterface
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import com.example.emanuelecalvisi.kotlinapp.Fragments.Person
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view

class MainActivity : AppCompatActivity() {

  var drawerOpened = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    main()

    nav_view.setNavigationItemSelectedListener { menuItem ->
      // set item as selected to persist highlight
      menuItem.isChecked = true
      // close drawer when item is tapped
      drawer_layout.closeDrawers()
      true
    }

    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setHomeAsUpIndicator(R.drawable.hamburger)

    val frag = MainFragment()
    ActivityUtil.configure(supportFragmentManager)
    ActivityUtil.addFragmentToActivity(frag)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      android.R.id.home -> {

        if (!drawerOpened) openDrawer()
        else closeDrawer()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
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

  fun openDrawer() {
    drawer_layout.openDrawer(GravityCompat.START)
    drawerOpened = true
  }

  fun closeDrawer() {
    drawer_layout.closeDrawer(GravityCompat.START)
    drawerOpened = false
  }

  fun main() {


    var Marco : Person? = null
    Marco = Person("Marco", "Rossi")


    println("Ciao "+Marco.firstName+" "+Marco._lastName)
    println("")

  }
}
