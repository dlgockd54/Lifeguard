package com.example.hclee.lifeguard.gallery

import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.util.Log

/**
 * Created by hclee on 2019-03-29.
 */

class PictureObserver(handler: Handler?, private val mListener: PictureUpdateListener): ContentObserver(handler) {
    private val TAG: String = PictureObserver::class.java.simpleName

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        Log.d(TAG, "onChange()")

        mListener.onPictureUpdate()
    }
}