package com.example.hclee.lifeguard.userprofile.listener

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.example.hclee.lifeguard.userprofile.adapter.UserProfilePagerAdapter
import com.example.hclee.lifeguard.userprofile.fragment.MedicalHistoryFragment

/**
 * Created by hclee on 2019-04-06.
 */

class OnPageChangeListenerImpl(private val mContext: Context, private val mAdapter: UserProfilePagerAdapter)
    : ViewPager.OnPageChangeListener {
    private val TAG: String = OnPageChangeListenerImpl::class.java.simpleName

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Log.d(TAG, "onPageScrolled()")

        val currentFragment: Fragment = mAdapter.getItem(0)

        (mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow((currentFragment as MedicalHistoryFragment).mAddEditText.windowToken, 0)
    }

    override fun onPageSelected(position: Int) {

    }
}