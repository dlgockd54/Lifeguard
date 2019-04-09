package com.example.hclee.lifeguard.contacts

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log

/**
 * Created by hclee on 2019-04-01.
 */

object SMSSendManager {
    private val TAG: String = SMSSendManager::class.java.simpleName
    private var mSmsManager: SmsManager? = null
    private var mLocationManager: LocationManager? = null
    private var mLocation: Location? = null
    private var mLatitude: Double = -1.0
    private var mLongitude: Double = -1.0
    private lateinit var mSMSContents: String

    init {
        mSmsManager = SmsManager.getDefault()
    }

    fun sendSMS(context: Context, phoneNumber: String) {
        Log.d(TAG, "sendSMS()")

        var textMessage: StringBuilder = StringBuilder(mSMSContents)

        if(mSmsManager == null) {
            Log.d(TAG, "init mSmsManager")

            mSmsManager = SmsManager.getDefault()
        }

        if(mLocationManager == null) {
            mLocationManager = (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocation = mLocationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER) // mLocation could be null
            }
        }
        else {
            mLocationManager?.let {
                mLocation = it.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
        }

        mLocation.let {
            pullLatLong(textMessage) // Does not append latitude and longitude, if mLocation is null
        }

        Log.d(TAG, "전송할 내용: $textMessage")

        mSmsManager.let {
            it?.sendTextMessage(phoneNumber,
                null,
                textMessage.toString(),
                null,
                null)
        }
    }

    private fun pullLatLong(textMessage: StringBuilder) {
        Log.d(TAG, "pullLatLong()")

        mLocation?.let {
            mLatitude = it.latitude
            mLongitude = it.longitude

            textMessage.append(" 제 현재 위치는 위도: $mLatitude, 경도: $mLongitude 입니다.")
        }
    }

    fun setSMSContents(contents: String) {
        Log.d(TAG, "setSMSContents()")

        mSMSContents = contents
    }
}