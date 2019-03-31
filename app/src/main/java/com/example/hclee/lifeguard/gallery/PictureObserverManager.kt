package com.example.hclee.lifeguard.gallery

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore
import com.example.hclee.lifeguard.gallery.listener.PictureUpdateListener

/**
 * Created by hclee on 2019-03-29.
 */

object PictureObserverManager {
    private val TAG: String = PictureObserverManager::class.java.simpleName
    private lateinit var mPresenter: GalleryPresenter
    private lateinit var mPictureObserver: PictureObserver
    // This handler handle onChange() function of PictureObserver on main thread to prevent WrongThreadException
    private val handler: Handler = Handler()

    fun registerObserver(presenter: GalleryPresenter, context: Context) {
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        mPresenter = presenter
        mPictureObserver = PictureObserver(handler, object:
            PictureUpdateListener {
            override fun onPictureUpdate() {
                mPresenter.resetList() // Create new picture list
                mPresenter.notifyPictureUpdate() // Notify to update view
            }
        })

        context.contentResolver.registerContentObserver(uri, true, mPictureObserver)
    }
}
