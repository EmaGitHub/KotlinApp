package com.example.emanuelecalvisi.kotlinapp.Fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.support.v4.content.res.ResourcesCompat.getColor
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emanuelecalvisi.kotlinapp.R
import android.widget.Toast
import com.example.emanuelecalvisi.kotlinapp.Utils.ActivityUtil
import com.example.emanuelecalvisi.kotlinapp.Utils.ListAdapter
import kotlinx.android.synthetic.main.fragment_async.view.rv_list
import kotlinx.android.synthetic.main.fragment_one.view.*
import java.time.Duration

/*

Created by Emanuele Calvisi on 31/07/2018.

*/

class AsyncFragment : CustomFragment(){

  var list: ArrayList<String> = ArrayList()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {

    val view = inflater?.inflate(R.layout.fragment_async, container, false)

    super.TAG = "AsyncFragment"


    addItems()
    view.rv_list.layoutManager = GridLayoutManager(context,3)
    // Access the RecyclerView Adapter and load the data into it
    view.rv_list.adapter = ListAdapter(list, context!!)




    return view
  }

  fun addItems() {
    list.add("dog")
    list.add("cat")
    list.add("owl")
    list.add("cheetah")
    list.add("raccoon")
    list.add("bird")
    list.add("snake")
    list.add("lizard")
    list.add("hamster")
    list.add("bear")
    list.add("lion")
    list.add("tiger")
    list.add("horse")
    list.add("frog")
    list.add("fish")
    list.add("shark")
    list.add("turtle")
    list.add("elephant")
    list.add("cow")
    list.add("beaver")
    list.add("bison")
    list.add("porcupine")
    list.add("rat")
    list.add("mouse")
    list.add("goose")
    list.add("deer")
    list.add("fox")
    list.add("moose")
    list.add("buffalo")
    list.add("monkey")
    list.add("penguin")
    list.add("parrot")
  }

}