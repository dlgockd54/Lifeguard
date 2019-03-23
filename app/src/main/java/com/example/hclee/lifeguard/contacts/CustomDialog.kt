package com.example.hclee.lifeguard.contacts

import android.app.Dialog
import android.content.Context
import android.util.Log
import com.example.hclee.lifeguard.R
import kotlinx.android.synthetic.main.custom_dialog.*

/**
 * Created by hclee on 2019-03-21.
 */

class CustomDialog(private val mContext: Context): Dialog(mContext) {
    private val TAG: String = CustomDialog::class.java.simpleName

    fun setProgress(value: Int) {
        Log.d(TAG, "setProgress()")

        progressbar.progress = value

        if(progressbar.progress < 100) {
            tv_text.setText(R.string.contact_loading)
        }
        else {
            tv_text.setText(R.string.contact_load_done)
        }

        Log.d(TAG, "text: ${tv_text.text}, val: ${progressbar.progress}")
    }
}