package com.example.hclee.lifeguard.userprofile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.hclee.lifeguard.R

class UserProfileActivity : AppCompatActivity(), UserProfileContract.View {
    private val TAG: String = UserProfileActivity::class.java.simpleName

    override lateinit var mPresenter: UserProfileContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
    }
}
