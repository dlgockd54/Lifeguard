package com.example.hclee.lifeguard.gallery

import com.example.hclee.lifeguard.AndroidThings
import com.example.hclee.lifeguard.BasePresenter
import com.example.hclee.lifeguard.BaseView

/**
 * Created by hclee on 2019-03-28.
 */

interface GalleryContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter {
        fun getPictureList(): List<Picture>
        fun pullPictureFromStorage(androidThings: AndroidThings)
        fun addPictureToList(picture: Picture)
        fun updateAdapter(picture: Picture)
    }
}