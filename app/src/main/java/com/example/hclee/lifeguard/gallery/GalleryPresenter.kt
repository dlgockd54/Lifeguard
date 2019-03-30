package com.example.hclee.lifeguard.gallery

import android.util.Log
import java.util.*

/**
 * Created by hclee on 2019-03-28.
 */

interface PictureLoadingListener {
    fun onAllPictureLoadingFinish()
    fun onPictureLoading(picture: Picture)
}

class GalleryPresenter(private val mActivity: GalleryContract.View, private val mAndroidThings: GalleryAndroidThings): GalleryContract.Presenter {
    private val TAG: String = GalleryPresenter::class.java.simpleName
    private var mPictureList: List<Picture> = LinkedList<Picture>()
    private val mPictureMap: HashMap<String, Picture> = HashMap<String, Picture>()
    private val mPictureObserverManager: PictureObserverManager = PictureObserverManager

    init {
        mPictureObserverManager.registerObserver(this, mAndroidThings.mContext)
    }

    override fun pullPictureFromStorage(galleryTask: GalleryTask) {
        galleryTask.execute()
    }

    private fun addPictureToList(picture: Picture) {
        Log.d(TAG, "addPictureToList()")

        mPictureMap[picture.mPictureDisplayName] = picture
        (mPictureList as LinkedList).add(picture)
    }

    private fun isListContainsPicture(picture: Picture): Boolean {
        return mPictureMap.containsKey(picture.mPictureDisplayName)
    }

    override fun updateAdapter(picture: Picture) {
        if(!isListContainsPicture(picture)) {
            addPictureToList(picture)
            mActivity.updateAdapter(picture)
        }
    }

    override fun getPictureList(): List<Picture> {
        return mPictureList
    }

    fun notifyPictureUpdate() {
        Log.d(TAG, "notifyPictureUpdate()")

        mActivity.notifyPictureUpdate()
    }

    fun resetList() {
        Log.d(TAG, "resetList()")

        mPictureList = LinkedList<Picture>()
        mPictureMap.clear()
    }
}