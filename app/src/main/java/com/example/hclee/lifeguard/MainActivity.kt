package com.example.hclee.lifeguard

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import com.example.hclee.lifeguard.contacts.ContactsActivity
import com.example.hclee.lifeguard.contacts.SMSSendManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName
    private val key: String = "sms_contents"
    private val permissionList: Array<String> = arrayOf(Manifest.permission.READ_CONTACTS,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.SEND_SMS,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_PHONE_STATE)
    private val PERMISSION_REQUEST_CODE: Int = 80
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate()")

        mToolbar = (main_toolbar as Toolbar)

        setSupportActionBar(mToolbar)

        // From Android Marshmallow, should request permission explicitly
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionList, PERMISSION_REQUEST_CODE)
        }

        restoreSharedPreferences()
    }

    private fun restoreSharedPreferences() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("lifeguard_sms_contents", MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        var smsContents: String = ""

        if(!sharedPreferences.contains(key)) {
            sharedPreferencesEditor.putString(key, "위급 상황입니다. 도와주세요. 119에 연락해주세요.")
            sharedPreferencesEditor.apply()
        }

        smsContents = sharedPreferences.getString(key, "위급 상황입니다. 도와주세요. 119에 연락해주세요.")

        sharedPreferencesEditor.apply()
        SMSSendManager.setSMSContents(smsContents)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.d(TAG, "onRequestPermissionResult()")

        val intent: Intent = Intent(applicationContext, ContactsActivity::class.java)

        if(requestCode == PERMISSION_REQUEST_CODE) {
            for(result in grantResults) {
                if(result == PackageManager.PERMISSION_DENIED) {
                    Log.d(TAG, "permission request result: PERMISSION_DENIED")

                    return
                }
            }
        }

        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")
    }
}
