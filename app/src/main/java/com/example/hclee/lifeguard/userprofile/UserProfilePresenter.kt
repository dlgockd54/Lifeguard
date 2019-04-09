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
    private val mTaskManager: TaskManager = TaskManager()

    override fun getMedicalHistoryList(): List<MedicalHistory> {
        return mMedicalHistoryList
    }

    override fun addMedicalHistoryToDatabse(medicalHistory: MedicalHistory) {
        Log.d(TAG, "addMedicalHistoryToDatabase()")

        DatabaseManager.insertMedicalHistory(medicalHistory.mDisease)
    }

    override fun pullMedicalHistoryFromDatabase(androidThings: MedicalHistoryAndroidThings) {
        mTaskManager.pullMedicalHistoryFromDatabase(androidThings)
    }

    override fun addMedicalHistoryToList(medicalHistory: MedicalHistory) {
        (mMedicalHistoryList as LinkedList).add(medicalHistory)
    }

    override fun removeMedicalHistoryFromDatabase(disease: String) {
        DatabaseManager.removeMedicalHistory(disease)
    }

    override fun removeMedicalHistoryFromList(disease: String) {
        (mMedicalHistoryList as LinkedList).remove(MedicalHistory(disease))
    }
}