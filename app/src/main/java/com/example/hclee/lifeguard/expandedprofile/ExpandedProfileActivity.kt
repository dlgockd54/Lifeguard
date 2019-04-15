package com.example.hclee.lifeguard.expandedprofile

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.hclee.lifeguard.R
import kotlinx.android.synthetic.main.activity_expanded_profile.*

class ExpandedProfileActivity : AppCompatActivity(), ExpandedProfileContract.View {
    private val TAG: String = ExpandedProfileActivity::class.java.simpleName

    override lateinit var mPresenter: ExpandedProfileContract.Presenter

    private lateinit var mToolbar: Toolbar
    private lateinit var mExpandedProfileImageView: ImageView
    private lateinit var mGlideRequestManager: RequestManager
    private lateinit var mProfileImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expanded_profile)

        Log.d(TAG, "onCreate()")

        init()
        setSupportActionBar(mToolbar)
    }

    private fun init() {
        mToolbar = (expanded_profile_image_toolbar as Toolbar)
        mPresenter = ExpandedProfilePresenter(this)
        mExpandedProfileImageView = iv_expanded_profile_image.apply {
            setOnClickListener {
                onBackPressed()
            }
        }
        mProfileImageUri = Uri.parse(intent.getStringExtra("profile_uri"))
        mGlideRequestManager = Glide.with(this)
        mGlideRequestManager
            .load(mProfileImageUri)
            .into(mExpandedProfileImageView)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        overridePendingTransition(R.anim.animation_slide_from_left, R.anim.animation_slide_to_right)
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")
    }
}
