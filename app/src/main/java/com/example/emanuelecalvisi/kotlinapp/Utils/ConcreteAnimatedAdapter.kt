package com.example.emanuelecalvisi.animlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.emanuelecalvisi.kotlinapp.R
import com.example.emanuelecalvisi.kotlinapp.Utils.*
import java.util.ArrayList


class GroupItem {
    internal var title: String? = null
    internal var items: MutableList<ChildItem> = ArrayList()
}

class ChildItem {
    internal var title: String? = null
    internal var hint: String? = null
}

private class ChildHolder {
    internal var title: TextView? = null
    internal var hint: TextView? = null
}

private class GroupHolder {
    internal var title: TextView? = null
}

/**
 * Adapter for our list of [GroupItem]s.
 */
class ConcreteAnimatedAdapter(context: Context) : AnimatedExpandableListAdapter() {

    val header: ArrayList<MenuModel> = ArrayList()
    val body: ArrayList<ArrayList<MenuModel>> = ArrayList()

    init{

        val mm0 = MenuModelHeader("kold", context.getDrawable(R.drawable.abc_list_focused_holo))
        val mm1 = MenuModelHeader("coap", context.getDrawable(R.drawable.ic_mtrl_chip_close_circle))
        val mm2 = MenuModelHeader("sdsf", context.getDrawable(R.drawable.ic_mtrl_chip_checked_circle))
        val mm3 = MenuModelHeader("click on Item", context.getDrawable(R.drawable.abc_ic_arrow_drop_right_black_24dp))
        val aa1 = MenuModelChild("aad", context.getDrawable(R.drawable.right_arrow))
        val aa3 = MenuModelChildToggle("wewe", context.getDrawable(R.drawable.hamburger), false)
        val kk1 = MenuModelFooter()

        val season0: ArrayList<MenuModel> = ArrayList()
        val season1: ArrayList<MenuModel> = ArrayList()
        val season2: ArrayList<MenuModel> = ArrayList()
        val season3: ArrayList<MenuModel> = ArrayList()
        season3.add(aa1)
        season3.add(aa1)
        season3.add(aa3)
        season3.add(aa1)
        val season4: ArrayList<MenuModel> = ArrayList()

        header.add(mm0)
        header.add(mm1)
        header.add(mm2)
        header.add(mm3)
        header.add(kk1)

        body.add(season0)
        body.add(season1)
        body.add(season2)
        body.add(season3)
        body.add(season4)


        val items = ArrayList<GroupItem>()
        // Populate our list with groups and it's children
        for (i in 0..header.size-1) {
            val item = GroupItem()

            item.title = (header.get(i) as MenuModelHeader).labelTitle

            for (j in 0 until body.get(i).size) {
                val child = ChildItem()
                child.title = (body.get(i).get(j) as MenuModelChild).labelTitle
                child.hint = ((body.get(i).get(j) as MenuModelChild).icon).toString()
                item.items.add(child)
            }
            items.add(item)
        }
        setData(items)
    }

    override fun getGroupCount(): Int {
        return items!!.size
    }

    private val inflater: LayoutInflater

    private var items: List<GroupItem>? = null

    init {
        inflater = LayoutInflater.from(context)
    }

    fun setData(items: List<GroupItem>) {
        this.items = items
    }

    override fun getChild(groupPosition: Int, childPosition: Int): ChildItem {
        return items!![groupPosition].items[childPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getRealChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ChildHolder
        val item = getChild(groupPosition, childPosition)
        if (convertView == null) {
            holder = ChildHolder()
            convertView = inflater.inflate(R.layout.list_group_child, parent, false)
            holder.title = convertView!!.findViewById(R.id.child) as TextView
            //holder.hint = convertView.findViewById(R.id.textHint) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ChildHolder
        }

        holder.title!!.text = item.title
        holder.hint!!.text = item.hint

        return convertView
    }

    override fun getRealChildrenCount(groupPosition: Int): Int {
        return items!![groupPosition].items.size
    }

    override fun getGroup(groupPosition: Int): GroupItem {
        return items!![groupPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: GroupHolder
        val item = getGroup(groupPosition)
        if (convertView == null) {
            holder = GroupHolder()
            convertView = inflater.inflate(R.layout.list_group_header, parent, false)
            holder.title = convertView!!.findViewById(R.id.header) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as GroupHolder
        }

        holder.title!!.text = item.title

        return convertView
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(arg0: Int, arg1: Int): Boolean {
        return true
    }

}