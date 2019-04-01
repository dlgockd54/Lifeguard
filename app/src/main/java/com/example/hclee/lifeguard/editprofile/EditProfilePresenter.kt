package com.example.hclee.lifeguard.editprofile

import android.net.Uri
import android.util.Log
import com.example.hclee.lifeguard.AndroidThings
import com.example.hclee.lifeguard.database.DatabaseManager

/**
 * Created by hclee on 2019-03-27.
 */

class EditProfilePresenter(val mEditProfileView: EditProfileContract.View, private val mAndroidThings: AndroidThings): EditProfileContract.Presenter {
    private val TAG: String = EditProfileActivity::class.java.simpleName

    override fun changeProfileImage(phoneNumber: String, uri: Uri) {
        Log.d(TAG, "changeProfileImage()")

        DatabaseManager.setProfileImageUriString(phoneNumber, uri)
    }

    override fun getProfileImageUriString(phoneNumber: String): String {
        var profileImageUriString: String = DatabaseManager.getProfileImageUriString(phoneNumber)

        return profileImageUriString
    }
}