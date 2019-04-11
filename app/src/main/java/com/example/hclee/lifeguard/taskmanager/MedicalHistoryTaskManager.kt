package com.example.hclee.lifeguard.taskmanager

import com.example.hclee.lifeguard.AndroidThings
import com.example.hclee.lifeguard.userprofile.MedicalHistoryAndroidThings

/**
 * Created by hclee on 2019-04-07.
 */

/**
 * UserProfilePresenter should not include Android involved codes.
 */
class MedicalHistoryTaskManager: TaskManager {
    private val TAG: String = MedicalHistoryTaskManager::class.java.simpleName

    override fun runTask(androidThings: AndroidThings) {
        pullMedicalHistoryFromDatabase(androidThings as MedicalHistoryAndroidThings)
    }

    private fun pullMedicalHistoryFromDatabase(androidThings: MedicalHistoryAndroidThings) {
        androidThings.mTask.execute()
    }
}