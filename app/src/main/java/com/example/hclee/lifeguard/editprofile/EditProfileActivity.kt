package com.example.hclee.lifeguard.editprofile

import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.hclee.lifeguard.AndroidThings
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.database.DatabaseManager
import com.example.hclee.lifeguard.database.ProfileImageOpenHelper
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity(), EditProfileContract.View {
    private val TAG: String = EditProfileActivity::class.java.simpleName

    override lateinit var mPresenter: EditProfileContract.Presenter

    lateinit var mProfileImageView: ImageView
    private lateinit var mProfileImageOpenHelper: ProfileImageOpenHelper
    private lateinit var mAndroidThings: AndroidThings
    private lateinit var mProfileImageDrawable: Drawable
    private lateinit var mGlideRequestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        Log.d(TAG, "string extra: ${intent.getStringExtra("phone_number")}")

        init()
    }

    private fun init() {
        mProfileImageView = iv_edit_profile.apply {
            setOnClickListener {
                Log.d(TAG, "onClick()")

                changeProfileImage()
            }
        }
        mProfileImageOpenHelper = ProfileImageOpenHelper(this, DatabaseManager.mDbName, null, 1)
        mGlideRequestManager = Glide.with(this)
        mAndroidThings = EditProfileAndroidThings(this, mProfileImageOpenHelper)
        mPresenter = EditProfilePresenter(this, mAndroidThings)
        mProfileImageDrawable = Drawable.createFromStream(contentResolver.openInputStream(Uri.parse(
            mPresenter.getProfileImageUriString(intent.getStringExtra("phone_number")))), intent.getStringExtra("phone_number"))
        mGlideRequestManager
            .load(mProfileImageDrawable)
            .circleCrop()
            .into(mProfileImageView)
    }

    private fun changeProfileImage() {
        Log.d(TAG, "changeProfileImage()")

        mPresenter.changeProfileImage()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        overridePendingTransition(R.anim.animation_slide_to_right, R.anim.animation_slide_from_left)
    }
}
