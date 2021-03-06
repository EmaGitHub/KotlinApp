package com.example.emanuelecalvisi.kotlinapp.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ExpandableListView.OnGroupClickListener
import java.util.*

/*
 * Copyright (C) 2014 Gary Guo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

class AnimatedExpandableListView : ExpandableListView {

    private var adapter: AnimatedExpandableListAdapter? = null

    internal val animationDuration: Int
        get() = ANIMATION_DURATION

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    init {
        setOnGroupClickListener(OnGroupClickListener { parent, v, groupPosition, id ->
            // We call collapseGroupWithAnimation(int) and
            // expandGroupWithAnimation(int) to animate group
            // expansion/collapse.
            if (isGroupExpanded(groupPosition)) {
                collapseGroupWithAnimation(groupPosition)
            } else {
                expandGroupWithAnimation(groupPosition)
            }
            true
        })
    }
    /**
     * @see ExpandableListView.setAdapter
     */
    override fun setAdapter(adapter: ExpandableListAdapter) {
        super.setAdapter(adapter)

        // Make sure that the adapter extends AnimatedExpandableListAdapter
        if (adapter is AnimatedExpandableListAdapter) {
            this.adapter = adapter
            this.adapter!!.setParent(this)
        } else {
            throw ClassCastException(adapter.toString() + " must implement AnimatedExpandableListAdapter")
        }
    }

    /**
     * Expands the given group with an animation.
     * @param groupPos The position of the group to expand
     * @return  Returns true if the group was expanded. False if the group was
     * already expanded.
     */
    @SuppressLint("NewApi")
    fun expandGroupWithAnimation(groupPos: Int): Boolean {
        val lastGroup = groupPos == adapter!!.groupCount - 1
        if (lastGroup && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return expandGroup(groupPos, true)
        }

        val groupFlatPos = getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPos))
        if (groupFlatPos != -1) {
            val childIndex = groupFlatPos - firstVisiblePosition
            if (childIndex < childCount) {
                // Get the view for the group is it is on screen...
                val v = getChildAt(childIndex)
                if (v.bottom >= bottom) {
                    // If the user is not going to be able to see the animation
                    // we just expand the group without an animation.
                    // This resolves the case where getChildView will not be
                    // called if the children of the group is not on screen

                    // We need to notify the adapter that the group was expanded
                    // without it's knowledge
                    adapter!!.notifyGroupExpanded(groupPos)
                    return expandGroup(groupPos)
                }
            }
        }

        // Let the adapter know that we are starting the animation...
        adapter!!.startExpandAnimation(groupPos, 0)
        // Finally call expandGroup (note that expandGroup will call
        // notifyDataSetChanged so we don't need to)
        return expandGroup(groupPos)
    }

    /**
     * Collapses the given group with an animation.
     * @param groupPos The position of the group to collapse
     * @return  Returns true if the group was collapsed. False if the group was
     * already collapsed.
     */
    fun collapseGroupWithAnimation(groupPos: Int): Boolean {
        val groupFlatPos = getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPos))
        if (groupFlatPos != -1) {
            val childIndex = groupFlatPos - firstVisiblePosition
            if (childIndex >= 0 && childIndex < childCount) {
                // Get the view for the group is it is on screen...
                val v = getChildAt(childIndex)
                if (v.bottom >= bottom) {
                    // If the user is not going to be able to see the animation
                    // we just collapse the group without an animation.
                    // This resolves the case where getChildView will not be
                    // called if the children of the group is not on screen
                    return collapseGroup(groupPos)
                }
            } else {
                // If the group is offscreen, we can just collapse it without an
                // animation...
                return collapseGroup(groupPos)
            }
        }

        // Get the position of the firstChild visible from the top of the screen
        val packedPos = getExpandableListPosition(firstVisiblePosition)
        var firstChildPos = ExpandableListView.getPackedPositionChild(packedPos)
        val firstGroupPos = ExpandableListView.getPackedPositionGroup(packedPos)

        // If the first visible view on the screen is a child view AND it's a
        // child of the group we are trying to collapse, then set that
        // as the first child position of the group... see
        // {@link #startCollapseAnimation(int, int)} for why this is necessary
        firstChildPos = if (firstChildPos == -1 || firstGroupPos != groupPos) 0 else firstChildPos

        // Let the adapter know that we are going to start animating the
        // collapse animation.
        adapter!!.startCollapseAnimation(groupPos, firstChildPos)

        // Force the listview to refresh it's views
        adapter!!.notifyDataSetChanged()
        return isGroupExpanded(groupPos)
    }

    /**
     * Used for holding information regarding the group.
     */
    internal class GroupInfo {
        internal var animating = false
        internal var expanding = false
        internal var firstChildPosition: Int = 0

        /**
         * This variable contains the last known height value of the dummy view.
         * We save this information so that if the user collapses a group
         * before it fully expands, the collapse animation will start from the
         * CURRENT height of the dummy view and not from the full expanded
         * height.
         */
        internal var dummyHeight = -1
    }

    internal class DummyView(context: Context) : View(context) {
        private val views = ArrayList<View>()
        private var divider: Drawable? = null
        private var dividerWidth: Int = 0
        private var dividerHeight: Int = 0

        fun setDivider(divider: Drawable?, dividerWidth: Int, dividerHeight: Int) {
            if (divider != null) {
                this.divider = divider
                this.dividerWidth = dividerWidth
                this.dividerHeight = dividerHeight

                divider.setBounds(0, 0, dividerWidth, dividerHeight)
            }
        }

        /**
         * Add a view for the DummyView to draw.
         * @param childView View to draw
         */
        fun addFakeView(childView: View) {
            childView.layout(0, 0, width, childView.measuredHeight)
            views.add(childView)
        }

        override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
            super.onLayout(changed, left, top, right, bottom)
            val len = views.size
            for (i in 0 until len) {
                val v = views[i]
                v.layout(left, top, left + v.measuredWidth, top + v.measuredHeight)
            }
        }

        fun clearViews() {
            views.clear()
        }

        public override fun dispatchDraw(canvas: Canvas) {
            canvas.save()
            if (divider != null) {
                divider!!.setBounds(0, 0, dividerWidth, dividerHeight)
            }

            val len = views.size
            for (i in 0 until len) {
                val v = views[i]

                canvas.save()
                canvas.clipRect(0, 0, width, v.measuredHeight)
                v.draw(canvas)
                canvas.restore()

                if (divider != null) {
                    divider!!.draw(canvas)
                    canvas.translate(0f, dividerHeight.toFloat())
                }

                canvas.translate(0f, v.measuredHeight.toFloat())
            }

            canvas.restore()
        }
    }

    internal class ExpandAnimation internal constructor(private val view: View, private val baseHeight: Int, endHeight: Int, private val groupInfo: GroupInfo) : Animation() {
        private val delta: Int

        init {
            delta = endHeight - baseHeight

            view.layoutParams.height = baseHeight
            view.requestLayout()
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            super.applyTransformation(interpolatedTime, t)
            if (interpolatedTime < 1.0f) {
                val `val` = baseHeight + (delta * interpolatedTime).toInt()
                view.layoutParams.height = `val`
                groupInfo.dummyHeight = `val`
                view.requestLayout()
            } else {
                val `val` = baseHeight + delta
                view.layoutParams.height = `val`
                groupInfo.dummyHeight = `val`
                view.requestLayout()
            }
        }
    }

    companion object {
        /*
     * A detailed explanation for how this class works:
     *
     * Animating the ExpandableListView was no easy task. The way that this
     * class does it is by exploiting how an ExpandableListView works.
     *
     * Normally when {@link ExpandableListView#collapseGroup(int)} or
     * {@link ExpandableListView#expandGroup(int)} is called, the view toggles
     * the flag for a group and calls notifyDataSetChanged to cause the ListView
     * to refresh all of it's view. This time however, depending on whether a
     * group is expanded or collapsed, certain childViews will either be ignored
     * or added to the list.
     *
     * Knowing this, we can come up with a way to animate our views. For
     * instance for group expansion, we tell the adapter to animate the
     * children of a certain group. We then expand the group which causes the
     * ExpandableListView to refresh all views on screen. The way that
     * ExpandableListView does this is by calling getView() in the adapter.
     * However since the adapter knows that we are animating a certain group,
     * instead of returning the real views for the children of the group being
     * animated, it will return a fake dummy view. This dummy view will then
     * draw the real child views within it's dispatchDraw function. The reason
     * we do this is so that we can animate all of it's children by simply
     * animating the dummy view. After we complete the animation, we tell the
     * adapter to stop animating the group and call notifyDataSetChanged. Now
     * the ExpandableListView is forced to refresh it's views again, except this
     * time, it will get the real views for the expanded group.
     *
     * So, to list it all out, when {@link #expandGroupWithAnimation(int)} is
     * called the following happens:
     *
     * 1. The ExpandableListView tells the adapter to animate a certain group.
     * 2. The ExpandableListView calls expandGroup.
     * 3. ExpandGroup calls notifyDataSetChanged.
     * 4. As an result, getChildView is called for expanding group.
     * 5. Since the adapter is in "animating mode", it will return a dummy view.
     * 6. This dummy view draws the actual children of the expanding group.
     * 7. This dummy view's height is animated from 0 to it's expanded height.
     * 8. Once the animation completes, the adapter is notified to stop
     *    animating the group and notifyDataSetChanged is called again.
     * 9. This forces the ExpandableListView to refresh all of it's views again.
     * 10.This time when getChildView is called, it will return the actual
     *    child views.
     *
     * For animating the collapse of a group is a bit more difficult since we
     * can't call collapseGroup from the start as it would just ignore the
     * child items, giving up no chance to do any sort of animation. Instead
     * what we have to do is play the animation first and call collapseGroup
     * after the animation is done.
     *
     * So, to list it all out, when {@link #collapseGroupWithAnimation(int)} is
     * called the following happens:
     *
     * 1. The ExpandableListView tells the adapter to animate a certain group.
     * 2. The ExpandableListView calls notifyDataSetChanged.
     * 3. As an result, getChildView is called for expanding group.
     * 4. Since the adapter is in "animating mode", it will return a dummy view.
     * 5. This dummy view draws the actual children of the expanding group.
     * 6. This dummy view's height is animated from it's current height to 0.
     * 7. Once the animation completes, the adapter is notified to stop
     *    animating the group and notifyDataSetChanged is called again.
     * 8. collapseGroup is finally called.
     * 9. This forces the ExpandableListView to refresh all of it's views again.
     * 10.This time when the ListView will not get any of the child views for
     *    the collapsed group.
     */

        private val TAG = AnimatedExpandableListAdapter::class.java.simpleName

        /**
         * The duration of the expand/collapse animations
         */
        private val ANIMATION_DURATION = 300
    }
}
