package com.example.hclee.lifeguard.gallery

import com.example.hclee.lifeguard.AndroidThings

/**
 * Created by hclee on 2019-03-28.
 */

interface PictureLoadingListener {
    fun onAllPictureLoadingFinish()
    fun onPictureLoading(picture: Picture)
}

class GalleryPresenter(private val mActivity: GalleryContract.View): GalleryContract.Presenter {
    private val TAG: String = GalleryPresenter::class.java.simpleName
    private val mPictureList: List<Picture> = ArrayList<Picture>()

    override fun pullPictureFromStorage(androidThings: AndroidThings) {
        (androidThings as GalleryAndroidThings).mGalleryTask.execute()
    }

    override fun addPictureToList(picture: Picture) {
        (mPictureList as ArrayList).add(picture)
    }

    override fun updateAdapter(picture: Picture) {
        (mActivity as GalleryActivity).updateAdapter(picture)
    }

    override fun getPictureList(): List<Picture> {
        return mPictureList
    }
}