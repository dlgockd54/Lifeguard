package com.example.hclee.lifeguard.database.listener

/**
 * Created by hclee on 2019-04-08.
 */

interface MedicalHistoryObserverListener: DatabaseObserverListener {
    fun onChange(disease: String)
}