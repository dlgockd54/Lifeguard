package com.example.hclee.lifeguard.userprofile.fragment


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.contacts.SMSSendManager
import com.example.hclee.lifeguard.userprofile.UserProfileActivity

/**
 * Created by hclee on 2019-04-05.
 */

class SMSSettingFragment : Fragment() {
    private val TAG: String = SMSSettingFragment::class.java.simpleName
    private val key: String = "lifeguard_sms_contents"

    private lateinit var mSMSEditText: EditText
    private lateinit var mActivity: UserProfileActivity
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var mSharedPreferencesEditor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_smssetting, container, false)

        init(rootView)
        restoreSMSContents()

        return rootView
    }

    private fun init(rootView: View) {
        mSMSEditText = rootView.findViewById(R.id.et_sms_contents)
        mActivity = activity as UserProfileActivity
        mSharedPreferences = mActivity.getSharedPreferences("lifeguard_sms_contents", Context.MODE_PRIVATE)
        mSharedPreferencesEditor = mSharedPreferences.edit()
    }

    private fun modifySMSContents() {
        val newSMSContents: String = mSMSEditText.text.toString()

        mSharedPreferencesEditor.putString(key, newSMSContents)
        SMSSendManager.setSMSContents(newSMSContents)
        mSharedPreferencesEditor.apply()
    }

    private fun restoreSMSContents() {
        var smsContents: String = ""

        smsContents = mSharedPreferences.getString(key, "위급 상황입니다. 도와주세요. 119에 연락해주세요.")
        mSMSEditText.text = SpannableStringBuilder(smsContents)

        mSharedPreferencesEditor.apply()
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause()")

        modifySMSContents()
    }
}
