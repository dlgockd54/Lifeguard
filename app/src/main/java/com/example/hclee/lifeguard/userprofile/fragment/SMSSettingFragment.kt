package com.example.hclee.lifeguard.userprofile.fragment


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.contacts.SMSSendManager
import com.example.hclee.lifeguard.userprofile.MedicalHistory
import com.example.hclee.lifeguard.userprofile.MedicalHistoryAndroidThings
import com.example.hclee.lifeguard.userprofile.MedicalHistoryTask
import com.example.hclee.lifeguard.userprofile.UserProfileActivity
import com.example.hclee.lifeguard.userprofile.listener.MedicalHistoryLoadListener

/**
 * Created by hclee on 2019-04-05.
 */

class SMSSettingFragment : Fragment() {
    private val TAG: String = SMSSettingFragment::class.java.simpleName
    private val key: String = "lifeguard_sms_contents"

    private lateinit var mRootView: View
    private lateinit var mSMSEditText: EditText
    private lateinit var mAddHMedicalistorySMSContentsButton: Button
    private lateinit var mActivity: UserProfileActivity
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var mSharedPreferencesEditor: SharedPreferences.Editor
    private lateinit var mMedicalHistorySMSContents: StringBuilder
    private lateinit var mUnEditedSMSContents: StringBuilder
    private val mMedicalHistoryLoadListener: MedicalHistoryLoadListener = object: MedicalHistoryLoadListener {
        override fun onMedicalHistoryLoaded(medicalHistory: MedicalHistory) {
            Log.d(TAG, "onMedicalHistoryLoaded()")

            createMedicalHistorySMSContents(medicalHistory.mDisease)
        }

        override fun onAllMedicalHistoryLoaded() {
            Log.d(TAG, "onAllMedicalHistoryLoaded()")

            appendMedicalHistorySMSContents()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_smssetting, container, false)

        init()
        restoreSMSContents()

        return mRootView
    }

    private fun init() {
        mAddHMedicalistorySMSContentsButton = mRootView.findViewById(R.id.btn_add_medical_history_sms_contents)
        mSMSEditText = mRootView.findViewById(R.id.et_sms_contents)
        mActivity = activity as UserProfileActivity
        mSharedPreferences = mActivity.getSharedPreferences("lifeguard_sms_contents", Context.MODE_PRIVATE)
        mSharedPreferencesEditor = mSharedPreferences.edit()

        mSMSEditText.setTextColor(Color.rgb(45, 48, 49))
        mAddHMedicalistorySMSContentsButton.run {
            setOnClickListener {
                Log.d(TAG, "onCheckChanged()")

                if(mActivity.mPresenter.getMedicalHistoryList().isNotEmpty()) {
                    initMedicalHistorySMSContents()
                    pullMedicalHistoryFromDatabase()
                }
                else {
                    Snackbar.make(mRootView, "추가한 과거 병력이 없습니다", Snackbar.LENGTH_LONG).apply {
                        setAction("확인") {
                            dismiss()
                        }

                        show()
                    }
                }

                modifySMSContents()
            }
        }
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
        mUnEditedSMSContents = StringBuilder(smsContents)

        mSharedPreferencesEditor.apply()
    }

    private fun pullMedicalHistoryFromDatabase() {
        mActivity.mPresenter.pullMedicalHistoryFromDatabase(
                MedicalHistoryAndroidThings(MedicalHistoryTask(mActivity, mMedicalHistoryLoadListener)))
    }

    private fun initMedicalHistorySMSContents() {
        mMedicalHistorySMSContents = StringBuilder("\n\n저는 아래와 같은 과거 병력이 있습니다.\n")
    }

    private fun createMedicalHistorySMSContents(appendStr: String) {
        mMedicalHistorySMSContents.append("- $appendStr\n")
    }

    private fun appendMedicalHistorySMSContents() {
        val smsContents: String = mSMSEditText.text.toString() + mMedicalHistorySMSContents

        mSMSEditText.text.clear()
        mSMSEditText.text = SpannableStringBuilder(smsContents)
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause()")

        modifySMSContents()
    }
}
