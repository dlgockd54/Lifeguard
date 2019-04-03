package com.example.hclee.lifeguard.editprofile

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.hclee.lifeguard.AndroidThings
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.database.DatabaseManager
import com.example.hclee.lifeguard.database.ProfileImageOpenHelper
import com.example.hclee.lifeguard.expandedprofile.ExpandedProfileActivity
import com.example.hclee.lifeguard.gallery.GalleryActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity(), EditProfileContract.View {
    private val TAG: String = EditProfileActivity::class.java.simpleName
    private val PROFILE_REQUEST_CODE = 919

    override lateinit var mPresenter: EditProfileContract.Presenter

    lateinit var mProfileImageView: ImageView
    lateinit var mChangeTextView: TextView
    private lateinit var mProfileImageOpenHelper: ProfileImageOpenHelper
    private lateinit var mAndroidThings: AndroidThings
    private lateinit var mProfileImageDrawable: Drawable
    private lateinit var mGlideRequestManager: RequestManager
    private lateinit var mToolbar: Toolbar
    private lateinit var mPhoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        Log.d(TAG, "string extra: ${intent.getStringExtra("phone_number")}")

        init()
        setSupportActionBar(mToolbar)
    }

    private fun init() {
        mToolbar = (edit_profile_toolbar as Toolbar)
        mProfileImageView = iv_profile_image
        mChangeTextView = tv_change_profile
        mProfileImageOpenHelper = ProfileImageOpenHelper(this, DatabaseManager.mDbName, null, 1)
        mGlideRequestManager = Glide.with(this)
        mAndroidThings = EditProfileAndroidThings(this, mProfileImageOpenHelper)
        mPresenter = EditProfilePresenter(this, mAndroidThings)
        mPhoneNumber = intent.getStringExtra("phone_number")
        mProfileImageDrawable = Drawable.createFromStream(contentResolver.openInputStream(Uri.parse(
            mPresenter.getProfileImageUriString(mPhoneNumber))), mPhoneNumber)
        mGlideRequestManager
            .load(mProfileImageDrawable)
            .circleCrop()
            .into(mProfileImageView)
    }

    fun onClick(view: View?) {
        Log.d(TAG, "onClick()")

        when(view?.id) {
            (mProfileImageView.id) -> {
                Log.d(TAG, "image")

                // Show profile image big
                showProfileImage()
            }
            (mChangeTextView.id) -> {
                Log.d(TAG, "text")

                selectProfileImage()
            }
            else -> {
                Log.d(TAG, "unknown view id")
            }
        }
    }

    private fun showProfileImage() {
        val componentName: ComponentName = ComponentName(this, ExpandedProfileActivity::class.java)
        val intent: Intent = Intent().apply {
            component = componentName
            putExtra("phone_number", mPhoneNumber)
        }

        startActivity(intent)
        overridePendingTransition(R.anim.animation_slide_from_right, R.anim.animation_slide_to_left)
    }

    private fun selectProfileImage() {
        Log.d(TAG, "selectProfileImage()")

        val intent: Intent = Intent(this, GalleryActivity::class.java)

        startActivityForResult(intent, PROFILE_REQUEST_CODE)
        overridePendingTransition(R.anim.animation_slide_from_right, R.anim.animation_slide_to_left)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult()")

        when(requestCode) {
            PROFILE_REQUEST_CODE -> {
                Log.d(TAG, "resultCode: $resultCode")

                if(resultCode == Activity.RESULT_OK) {
                    val resultIntent: Intent? = data
                    val profileUri: Uri? = Uri.parse(resultIntent?.getStringExtra("picture_uri"))

                    profileUri.let {
                        changeProfileImage(profileUri!!)
                    }
                }
            }
        }
    }

    private fun changeProfileImage(uri: Uri) {
        Log.d(TAG, uri.toString())

        mPresenter.changeProfileImage(mPhoneNumber, uri)
        mProfileImageDrawable = Drawable.createFromStream(contentResolver.openInputStream(Uri.parse(
            mPresenter.getProfileImageUriString(mPhoneNumber))), mPhoneNumber)
        mGlideRequestManager
            .load(mProfileImageDrawable)
            .circleCrop()
            .into(mProfileImageView)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        overridePendingTransition(R.anim.animation_slide_from_left, R.anim.animation_slide_to_right)
    }
}
