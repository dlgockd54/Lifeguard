package com.example.hclee.lifeguard.userprofile

/**
 * Created by hclee on 2019-04-07.
 */

/**
 * UserProfilePresenter should not include Android involved codes.
 */
class TaskManager {
    private val TAG: String = TaskManager::class.java.simpleName

    fun pullMedicalHistoryFromDatabase(androidThings: MedicalHistoryAndroidThings) {
        androidThings.mTask.execute()
    }
}