package com.example.hclee.lifeguard.gallery

import com.example.hclee.lifeguard.BasePresenter
import com.example.hclee.lifeguard.BaseView

/**
 * Created by hclee on 2019-03-28.
 */

interface GalleryContract {
    interface View: BaseView<Presenter> {
        fun updateAdapter(picture: Picture)
        fun notifyPictureUpdate()
    }

    interface Presenter: BasePresenter {
        fun getPictureList(): List<Picture>
        fun pullPictureFromStorage(galleryTask: GalleryTask)
        fun updateAdapter(picture: Picture)
    }
}