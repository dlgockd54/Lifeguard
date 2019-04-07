package com.example.hclee.lifeguard.userprofile.listener

import com.example.hclee.lifeguard.userprofile.MedicalHistory

/**
 * Created by hclee on 2019-04-07.
 */

interface MedicalHistoryLoadListener {
    fun onMedicalHistoryLoaded(medicalHistory: MedicalHistory)
    fun onAllMedicalHistoryLoaded()
}