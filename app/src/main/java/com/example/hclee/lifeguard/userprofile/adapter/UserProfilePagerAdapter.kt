package com.example.hclee.lifeguard.userprofile.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.hclee.lifeguard.userprofile.fragment.MedicalHistoryFragment
import com.example.hclee.lifeguard.userprofile.fragment.SMSSettingFragment

/**
 * Created by hclee on 2019-04-05.
 */

class UserProfilePagerAdapter(private val mFragmentManger: FragmentManager): FragmentPagerAdapter(mFragmentManger) {
    private val TAG: String = UserProfilePagerAdapter::class.java.simpleName
//    private val mFragmentList: ArrayList<Pair<Fragment, String>> = ArrayList<Pair<Fragment, String>>()
    private val mFragmentList: ArrayList<Fragment> = ArrayList<Fragment>()
    private val mPageTitleArr: Array<String> = arrayOf("과거 병력 설정", "문자 메시지 설정")

    init {
        mFragmentList.add(MedicalHistoryFragment())
        mFragmentList.add(SMSSettingFragment())
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return mFragmentList[0]
            }
            1 -> {
                return mFragmentList[1]
            }
        }

        return mFragmentList[0]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mPageTitleArr[position]
    }
}