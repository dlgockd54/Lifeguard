package com.example.hclee.lifeguard.contacts.adapter

import android.util.Log
import com.daimajia.swipe.SwipeLayout

/**
 * Created by hclee on 2019-03-25.
 */

class SwipeListenerImpl: SwipeLayout.SwipeListener {
    private val TAG: String = SwipeListenerImpl::class.java.simpleName

    /**
     * When bottom view totally show.
     */
    override fun onOpen(layout: SwipeLayout?) {
        Log.d(TAG, "onOpen()")


    }

    /**
     * When surface view totally cover the bottom view.
     * Do nothing.
     */
    override fun onClose(layout: SwipeLayout?) {
        Log.d(TAG, "onClose()")
    }

    /**
     * When Swiping.
     */
    override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {
        Log.d(TAG, "onUpdate()")
    }

    override fun onStartOpen(layout: SwipeLayout?) {

    }

    override fun onStartClose(layout: SwipeLayout?) {

    }

    override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {

    }
}