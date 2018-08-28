package com.example.emanuelecalvisi.kotlinapp.Utils

/*

Created by Emanuele Calvisi on 28/08/2018.

*/

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emanuelecalvisi.kotlinapp.R
import kotlinx.android.synthetic.main.recycler_view_item.view.item

class ListAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    holder?.itemTextView?.text = items.get(position)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false))
  }

  override fun getItemCount(): Int {
    return items.size
  }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

  val itemTextView = view.item
}