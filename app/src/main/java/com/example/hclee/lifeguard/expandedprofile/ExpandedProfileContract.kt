package com.example.hclee.lifeguard.expandedprofile

import com.example.hclee.lifeguard.BasePresenter
import com.example.hclee.lifeguard.BaseView

/**
 * Created by hclee on 2019-04-01.
 */

interface ExpandedProfileContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter {
        fun getProfileImageUriString(phoneNumber: String): String
    }
}