package com.example.hclee.lifeguard.editprofile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.example.hclee.lifeguard.R

class EditProfileActivity : AppCompatActivity() {
    private val TAG: String = EditProfileActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        Toast.makeText(applicationContext, "EditProfileActivity", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        overridePendingTransition(R.anim.animation_slide_to_right, R.anim.animation_slide_from_left)
    }
}
