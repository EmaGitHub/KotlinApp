package com.example.emanuelecalvisi.kotlinapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.emanuelecalvisi.kotlinapp.Fragments.MainFragment
import android.content.DialogInterface
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import android.widget.Toast
import com.example.emanuelecalvisi.animlist.ConcreteAnimatedAdapter
import com.example.emanuelecalvisi.kotlinapp.Utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    val header: ArrayList<MenuModel> = ArrayList()
    val body: ArrayList<ArrayList<MenuModel>> = ArrayList()

    var drawerOpened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mm0 = MenuModelHeader("kold", resources.getDrawable(R.drawable.abc_list_focused_holo))
        val mm1 = MenuModelHeader("coap", resources.getDrawable(R.drawable.ic_mtrl_chip_close_circle))
        val mm2 = MenuModelHeader("sdsf", resources.getDrawable(R.drawable.ic_mtrl_chip_checked_circle))
        val mm3 = MenuModelHeader("khgf", resources.getDrawable(R.drawable.abc_ic_arrow_drop_right_black_24dp))
        val aa1 = MenuModelChild("aad", resources.getDrawable(R.drawable.right_arrow))
        val aa3 = MenuModelChildToggle("wewe", resources.getDrawable(R.drawable.hamburger), false)
        val kk1 = MenuModelFooter()

        val season0: ArrayList<MenuModel> = ArrayList()
        val season1: ArrayList<MenuModel> = ArrayList()
        val season2: ArrayList<MenuModel> = ArrayList()
        val season3: ArrayList<MenuModel> = ArrayList()
        season3.add(aa1)
        season3.add(aa1)
        season3.add(aa3)
        season3.add(aa1)

        header.add(mm0)
        header.add(mm1)
        header.add(mm2)
        header.add(mm3)
        header.add(kk1)

        body.add(season0)
        body.add(season1)
        body.add(season2)
        body.add(season3)

        expandableListView.setGroupIndicator(null)
        expandableListView.dividerHeight = 0


        //expandableListView.setAdapter(animatedExpandableListAdapter)
        var adapter = ConcreteAnimatedAdapter(baseContext)
        expandableListView.setAdapter(adapter)

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
        toast("Activity started")
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

    fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

}
