package com.example.hclee.lifeguard.expandedprofile

import com.example.hclee.lifeguard.database.DatabaseManager

/**
 * Created by hclee on 2019-04-01.
 */

class ExpandedProfilePresenter(private val mActivity: ExpandedProfileContract.View)
    : ExpandedProfileContract.Presenter {

    override fun getProfileImageUriString(phoneNumber: String): String {
        return DatabaseManager.getProfileImageUriString(phoneNumber)
    }
}