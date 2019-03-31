package com.example.hclee.lifeguard.gallery.listener

import com.example.hclee.lifeguard.gallery.Picture

/**
 * Created by hclee on 2019-03-31.
 */

interface PictureLoadingListener {
    fun onAllPictureLoadingFinish()
    fun onPictureLoading(picture: Picture)
}