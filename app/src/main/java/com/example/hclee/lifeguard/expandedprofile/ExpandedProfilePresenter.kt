package com.example.hclee.lifeguard.expandedprofile

import android.net.Uri
import com.example.hclee.lifeguard.database.DatabaseManager

/**
 * Created by hclee on 2019-04-01.
 */

class ExpandedProfilePresenter(private val mActivity: ExpandedProfileContract.View)
    : ExpandedProfileContract.Presenter {

    override fun getProfileImageUri(phoneNumber: String): Uri {
        return DatabaseManager.getProfileImageUri(phoneNumber)
    }
}