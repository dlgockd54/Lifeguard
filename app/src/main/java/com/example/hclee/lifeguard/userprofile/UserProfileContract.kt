package com.example.hclee.lifeguard.userprofile

import com.example.hclee.lifeguard.BasePresenter
import com.example.hclee.lifeguard.BaseView

/**
 * Created by hclee on 2019-04-05.
 */

interface UserProfileContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter {
        fun getMedicalHistoryList(): List<MedicalHistory>
        fun pullMedicalHistoryFromDatabase(androidThings: MedicalHistoryAndroidThings)
        fun addMedicalHistoryToDatabse(medicalHistory: MedicalHistory)
        fun addMedicalHistoryToList(medicalHistory: MedicalHistory)
        fun removeMedicalHistoryFromDatabase(disease: String)
        fun removeMedicalHistoryFromList(disease: String)
    }
}