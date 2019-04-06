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
        fun updateAdapter()
        fun addMedicalHistoryToDatabse(medicalHistory: MedicalHistory)
    }
}