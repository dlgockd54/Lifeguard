package com.example.hclee.lifeguard.editprofile

import com.example.hclee.lifeguard.BasePresenter
import com.example.hclee.lifeguard.BaseView

/**
 * Created by hclee on 2019-03-27.
 */

interface EditProfileContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter {
        fun changeProfileImage()
        fun getProfileImageUriString(phoneNumber: String): String
    }
}