package com.example.hclee.lifeguard.expandedprofile

import android.net.Uri
import com.example.hclee.lifeguard.BasePresenter
import com.example.hclee.lifeguard.BaseView

/**
 * Created by hclee on 2019-04-01.
 */

interface ExpandedProfileContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter {
        fun getProfileImageUri(phoneNumber: String): Uri
    }
}