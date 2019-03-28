package com.example.hclee.lifeguard.editprofile

import android.util.Log
import com.example.hclee.lifeguard.AndroidThings
import com.example.hclee.lifeguard.database.DatabaseManager

/**
 * Created by hclee on 2019-03-27.
 */

class EditProfilePresenter(val mEditProfileView: EditProfileContract.View, private val mAndroidThings: AndroidThings): EditProfileContract.Presenter {
    private val TAG: String = EditProfileActivity::class.java.simpleName
    private val mDatabaseManager: DatabaseManager = DatabaseManager

    override fun changeProfileImage() {
        Log.d(TAG, "changeProfileImage()")


    }

    override fun getProfileImageUriString(phoneNumber: String): String {
        var profileImageUriString: String = DatabaseManager.getProfileImageUriString(phoneNumber)

        return profileImageUriString
    }
}