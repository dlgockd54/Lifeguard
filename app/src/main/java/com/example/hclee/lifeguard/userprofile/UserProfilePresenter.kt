package com.example.hclee.lifeguard.userprofile

import android.util.Log
import com.example.hclee.lifeguard.database.DatabaseManager
import java.util.*

/**
 * Created by hclee on 2019-04-05.
 */

class UserProfilePresenter(val mActivity: UserProfileContract.View): UserProfileContract.Presenter {
    private val TAG: String = UserProfilePresenter::class.java.simpleName
    private val mMedicalHistoryList: List<MedicalHistory> = LinkedList<MedicalHistory>()

    override fun getMedicalHistoryList(): List<MedicalHistory> {
        return mMedicalHistoryList
    }

    override fun addMedicalHistoryToDatabse(medicalHistory: MedicalHistory) {
        Log.d(TAG, "addMedicalHistoryToDatabase()")

        DatabaseManager.insertMedicalHistory(medicalHistory.mDisease)
    }

    override fun updateAdapter() {

    }
}