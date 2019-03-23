package com.example.hclee.lifeguard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hclee.lifeguard.contacts.ContactsActivity

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName
    private val permissionList: Array<String> = arrayOf(Manifest.permission.READ_CONTACTS,
        Manifest.permission.CALL_PHONE)
    private val PERMISSION_REQUEST_CODE: Int = 80

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate()")

        // From Android Marshmallow, should request permission explicitly
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionList, PERMISSION_REQUEST_CODE)
        }
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
