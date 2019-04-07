package com.example.hclee.lifeguard.userprofile

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.astuetz.PagerSlidingTabStrip
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.userprofile.adapter.UserProfilePagerAdapter
import com.example.hclee.lifeguard.userprofile.listener.OnPageChangeListenerImpl
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity(), UserProfileContract.View {
    private val TAG: String = UserProfileActivity::class.java.simpleName

    override lateinit var mPresenter: UserProfileContract.Presenter

    private lateinit var mToolbar: Toolbar
    private lateinit var mViewPager: ViewPager
    lateinit var mAdapter: UserProfilePagerAdapter
    private lateinit var mPagerSlidingTab: PagerSlidingTabStrip
    private lateinit var mPageChangeListener: OnPageChangeListenerImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        init()
    }

    private fun init() {
        mToolbar = (user_profile_toolbar as Toolbar)
        setSupportActionBar(mToolbar)
        mAdapter = UserProfilePagerAdapter(supportFragmentManager)
        mPresenter = UserProfilePresenter(this)
        mViewPager = view_pager_user_profile.apply {
            adapter = mAdapter
            currentItem = 0
        }
        mPageChangeListener = OnPageChangeListenerImpl(this, mAdapter)
        mPagerSlidingTab = pager_tab.apply {
            shouldExpand = true // If set to true, each tab is given the same weight, default false
            dividerColor = Color.GREEN
            underlineColor = Color.BLUE
            textSize = 50

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                indicatorColor = resources.getColor(R.color.colorTabIndicator, null)
                setBackgroundColor(resources.getColor(R.color.colorTabBackground, null))
            } else {
                indicatorColor = resources.getColor(R.color.colorTabIndicator)
                setBackgroundColor(resources.getColor(R.color.colorTabBackground))
            }

            setViewPager(mViewPager)
            setPadding(paddingLeft, 35, paddingRight, paddingBottom)
            setOnPageChangeListener(mPageChangeListener)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        overridePendingTransition(R.anim.animation_slide_from_left, R.anim.animation_slide_to_right)
    }
}
