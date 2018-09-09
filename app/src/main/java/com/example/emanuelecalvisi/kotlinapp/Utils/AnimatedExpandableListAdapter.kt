package com.example.emanuelecalvisi.kotlinapp.Utils

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.AbsListView
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView

/**
 * A specialized adapter for use with the AnimatedExpandableListView. All
 * adapters used with AnimatedExpandableListView MUST extend this class.
 */
open abstract class AnimatedExpandableListAdapter : BaseExpandableListAdapter() {
    private val groupInfo = SparseArray<AnimatedExpandableListView.GroupInfo>()
    private var parent: AnimatedExpandableListView? = null

    val realChildTypeCount: Int
        get() = 1

    internal fun setParent(parent: AnimatedExpandableListView) {
        this.parent = parent
    }

    fun getRealChildType(groupPosition: Int, childPosition: Int): Int {
        return 0
    }

    abstract fun getRealChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View
    abstract fun getRealChildrenCount(groupPosition: Int): Int

    private fun getGroupInfo(groupPosition: Int): AnimatedExpandableListView.GroupInfo {
        var info: AnimatedExpandableListView.GroupInfo? = groupInfo.get(groupPosition)
        if (info == null) {
            info = AnimatedExpandableListView.GroupInfo()
            groupInfo.put(groupPosition, info)
        }
        return info
    }

    fun notifyGroupExpanded(groupPosition: Int) {
        val info = getGroupInfo(groupPosition)
        info.dummyHeight = -1
    }

    internal fun startExpandAnimation(groupPosition: Int, firstChildPosition: Int) {
        val info = getGroupInfo(groupPosition)
        info.animating = true
        info.firstChildPosition = firstChildPosition
        info.expanding = true
    }

    internal fun startCollapseAnimation(groupPosition: Int, firstChildPosition: Int) {
        val info = getGroupInfo(groupPosition)
        info.animating = true
        info.firstChildPosition = firstChildPosition
        info.expanding = false
    }

    private fun stopAnimation(groupPosition: Int) {
        val info = getGroupInfo(groupPosition)
        info.animating = false
    }

    /**
     * Override [.getRealChildType] instead.
     */
    override fun getChildType(groupPosition: Int, childPosition: Int): Int {
        val info = getGroupInfo(groupPosition)
        return if (info.animating) {
            // If we are animating this group, then all of it's children
            // are going to be dummy views which we will say is type 0.
            0
        } else {
            // If we are not animating this group, then we will add 1 to
            // the type it has so that no type id conflicts will occur
            // unless getRealChildType() returns MAX_INT
            getRealChildType(groupPosition, childPosition) + 1
        }
    }

    /**
     * Override [.getRealChildTypeCount] instead.
     */
    override fun getChildTypeCount(): Int {
        // Return 1 more than the childTypeCount to account for DummyView
        return realChildTypeCount + 1
    }

    protected fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0)
    }

    /**
     * Override [.getChildView] instead.
     */
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        val info = getGroupInfo(groupPosition)

        if (info.animating) {
            // If this group is animating, return the a DummyView...
            if (convertView is AnimatedExpandableListView.DummyView == false) {
                convertView = AnimatedExpandableListView.DummyView(parent!!.context)
                convertView.setLayoutParams(AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 0))
            }

            if (childPosition < info.firstChildPosition) {
                // The reason why we do this is to support the collapse
                // this group when the group view is not visible but the
                // children of this group are. When notifyDataSetChanged
                // is called, the ExpandableListView tries to keep the
                // list position the same by saving the first visible item
                // and jumping back to that item after the views have been
                // refreshed. Now the problem is, if a group has 2 items
                // and the first visible item is the 2nd child of the group
                // and this group is collapsed, then the dummy view will be
                // used for the group. But now the group only has 1 item
                // which is the dummy view, thus when the ListView is trying
                // to restore the scroll position, it will try to jump to
                // the second item of the group. But this group no longer
                // has a second item, so it is forced to jump to the next
                // group. This will cause a very ugly visual glitch. So
                // the way that we counteract this is by creating as many
                // dummy views as we need to maintain the scroll position
                // of the ListView after notifyDataSetChanged has been
                // called.
                convertView?.layoutParams?.height = 0
                return convertView
            }

            val listView = parent as ExpandableListView

            val dummyView = convertView as AnimatedExpandableListView.DummyView

            // Clear the views that the dummy view draws.
            dummyView.clearViews()

            // Set the style of the divider
            dummyView.setDivider(listView.divider, parent.getMeasuredWidth(), listView.dividerHeight)

            // Make measure specs to measure child views
            val measureSpecW = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY)
            val measureSpecH = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)

            var totalHeight = 0
            val clipHeight = parent.getHeight()

            val len = getRealChildrenCount(groupPosition)
            for (i in info.firstChildPosition until len) {
                val childView = getRealChildView(groupPosition, i, i == len - 1, null, parent)

                var p: AbsListView.LayoutParams? = childView.layoutParams as AbsListView.LayoutParams
                if (p == null) {
                    p = generateDefaultLayoutParams() as AbsListView.LayoutParams
                    childView.layoutParams = p
                }

                val lpHeight = p.height

                val childHeightSpec: Int
                if (lpHeight > 0) {
                    childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
                } else {
                    childHeightSpec = measureSpecH
                }

                childView.measure(measureSpecW, childHeightSpec)
                totalHeight += childView.measuredHeight

                if (totalHeight < clipHeight) {
                    // we only need to draw enough views to fool the user...
                    dummyView.addFakeView(childView)
                } else {
                    dummyView.addFakeView(childView)

                    // if this group has too many views, we don't want to
                    // calculate the height of everything... just do a light
                    // approximation and break
                    val averageHeight = totalHeight / (i + 1)
                    totalHeight += (len - i - 1) * averageHeight
                    break
                }
            }

            val o: Any?
            o = dummyView.tag
            val state = if (o == null) STATE_IDLE else o as Int

            if (info.expanding && state != STATE_EXPANDING) {
                val ani = AnimatedExpandableListView.ExpandAnimation(dummyView, 0, totalHeight, info)
                ani.duration = this.parent!!.animationDuration.toLong()
                ani.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationEnd(animation: Animation) {
                        stopAnimation(groupPosition)
                        notifyDataSetChanged()
                        dummyView.tag = STATE_IDLE
                    }

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationStart(animation: Animation) {}

                })
                dummyView.startAnimation(ani)
                dummyView.tag = STATE_EXPANDING
            } else if (!info.expanding && state != STATE_COLLAPSING) {
                if (info.dummyHeight == -1) {
                    info.dummyHeight = totalHeight
                }

                val ani = AnimatedExpandableListView.ExpandAnimation(dummyView, info.dummyHeight, 0, info)
                ani.duration = this.parent!!.animationDuration.toLong()
                ani.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationEnd(animation: Animation) {
                        stopAnimation(groupPosition)
                        listView.collapseGroup(groupPosition)
                        notifyDataSetChanged()
                        info.dummyHeight = -1
                        dummyView.tag = STATE_IDLE
                    }

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationStart(animation: Animation) {}

                })
                dummyView.startAnimation(ani)
                dummyView.tag = STATE_COLLAPSING
            }

            return convertView
        } else {
            return getRealChildView(groupPosition, childPosition, isLastChild, convertView, parent!!)
        }
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        val info = getGroupInfo(groupPosition)
        return if (info.animating) {
            info.firstChildPosition + 1
        } else {
            getRealChildrenCount(groupPosition)
        }
    }

    companion object {

        private val STATE_IDLE = 0
        private val STATE_EXPANDING = 1
        private val STATE_COLLAPSING = 2
    }

}