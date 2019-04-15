package com.example.hclee.lifeguard.contacts

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log

class SMSSendingReceiver : BroadcastReceiver() {
    private val TAG: String = SMSSendingReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "resultCode: $resultCode")

        when(resultCode) {
            Activity.RESULT_OK -> {
                Log.d(TAG, "SMS sending OK")
            }
            SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                Log.d(TAG, "RESULT_ERROR_GENERICE_FAILURE")
            }
            SmsManager.RESULT_ERROR_NO_SERVICE -> {

            }
            SmsManager.RESULT_ERROR_NULL_PDU -> {

            }
            SmsManager.RESULT_ERROR_RADIO_OFF -> {

            }
        }
    }
}
