package com.example.hclee.lifeguard.editprofile

import android.content.Context
import com.example.hclee.lifeguard.AndroidThings
import com.example.hclee.lifeguard.database.LifeguardDatabaseOpenHelper

/**
 * Created by hclee on 2019-03-28.
 */

data class EditProfileAndroidThings(val mContext: Context, val mProfileImageOpenHelper: LifeguardDatabaseOpenHelper): AndroidThings