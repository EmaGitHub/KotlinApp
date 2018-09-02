package com.example.emanuelecalvisi.kotlinapp.Utils


import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.emanuelecalvisi.kotlinapp.R
import android.view.animation.Animation
import android.view.animation.Transformation
import com.example.emanuelecalvisi.kotlinapp.MainActivity


class ExpandableListAdapter(var context: Context, var expandableListView: ExpandableListView, var header: MutableList<MenuModel>, var body: MutableList<MutableList<MenuModel>>) : BaseExpandableListAdapter() {

    var selected: View? = null

    override fun getGroup(groupPosition: Int): MenuModel {
        return header[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }


    override fun getChildrenCount(groupPosition: Int): Int {
        return body[groupPosition].size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): MenuModel {
        return body[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }


    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return header.size
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            if (getGroup(groupPosition) is MenuModelFooter) {

                convertView = inflater.inflate(R.layout.list_group_header_footer, null)
                convertView.setOnClickListener {}

            } else {
                convertView = inflater.inflate(R.layout.list_group_header, null)
                val title = convertView?.findViewById<TextView>(R.id.header)
                val image = convertView?.findViewById<ImageView>(R.id.icon_group)
                title?.text = ((getGroup(groupPosition)) as MenuModelHeader).labelTitle
                image?.setImageDrawable(((getGroup(groupPosition)) as MenuModelHeader).icon)
                convertView.setOnClickListener {
                    if (expandableListView.isGroupExpanded(groupPosition))
                        expandableListView.collapseGroup(groupPosition)
                    else
                        expandableListView.expandGroup(groupPosition, true)
                    //expand(expandableListView)
                    Toast.makeText(context, (getGroup(groupPosition) as MenuModelHeader).labelTitle, Toast.LENGTH_SHORT).show()
                    headerAction(groupPosition, convertView)
                }
            }
        }
        return convertView
    }

    private fun headerAction(groupPosition: Int, view: View) {

        when (groupPosition) {
            0 -> select(view)
            1 -> select(view)
            2 -> select(view)
            3 -> Unit
        }
    }

    private fun select( view: View) {

        if(selected != null) {
            (selected as View).findViewById<TextView>(R.id.header).setTextColor(Color.BLACK)
            (selected as View).setBackgroundColor(Color.WHITE)
        }

        view.setBackgroundColor(Color.BLUE)
        view.findViewById<TextView>(R.id.header).setTextColor(Color.WHITE)
        selected = view

        (context as MainActivity).closeDrawer()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            if (getChild(groupPosition, childPosition) is MenuModelChildToggle){
                convertView = inflater.inflate(R.layout.list_group_child_toggle, null)

                val switch = convertView!!.findViewById<Switch>(R.id.switch1)
                if((getChild(groupPosition, childPosition) as MenuModelChildToggle).activated==true) {
                    switch.isChecked = true
                }
                else switch.isChecked = false

            }
            else convertView = inflater.inflate(R.layout.list_group_child, null)

        }
        val title = convertView?.findViewById<TextView>(R.id.child)
        title?.text = ((getChild(groupPosition, childPosition)) as MenuModelChild).labelTitle
        convertView?.setOnClickListener {
            Toast.makeText(context, (getChild(groupPosition, childPosition) as MenuModelChild).labelTitle, Toast.LENGTH_SHORT).show()
            childrenAction(childPosition)
        }
        return convertView
    }

    private fun childrenAction(childPosition: Int) {
        when (childPosition) {
            0 -> (context as MainActivity).closeDrawer()
            1 -> (context as MainActivity).closeDrawer()
            2 -> (context as MainActivity).closeDrawer()
            3 -> (context as MainActivity).closeDrawer()
        }
    }

    fun expand(v: View) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = v.measuredHeight

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.layoutParams.height = 1
        v.visibility = View.VISIBLE
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                v.layoutParams.height = if (interpolatedTime == 1f)
                    ViewGroup.LayoutParams.WRAP_CONTENT
                else
                    (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        a.duration = 300000
        v.startAnimation(a)
    }

    fun collapse(v: View) {
        val initialHeight = v.measuredHeight

        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        v.startAnimation(a)
    }

}