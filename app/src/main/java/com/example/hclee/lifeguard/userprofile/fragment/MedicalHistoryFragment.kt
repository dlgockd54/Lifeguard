package com.example.hclee.lifeguard.userprofile.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText

import com.example.hclee.lifeguard.R

/**
 * Created by hclee on 2019-04-05.
 */

class MedicalHistoryFragment : Fragment() {
    private val TAG: String = MedicalHistoryFragment::class.java.simpleName

    private lateinit var mAddHistoryButton: Button
    lateinit var mAddEditText: EditText
    private lateinit var mLayout: ViewGroup

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_medical_history, container, false)

        Log.d(TAG, "onCreateView()")

        mAddEditText = rootView.findViewById(R.id.et_add_history)
        mAddHistoryButton = rootView.findViewById(R.id.btn_add_medical_history)
        mAddHistoryButton.run {
            setOnClickListener {
                if(mAddEditText.visibility == View.INVISIBLE) {
                    mAddEditText.visibility = View.VISIBLE
                }
            }
        }
        mLayout = rootView.findViewById(R.id.layout_medical_history)
        mLayout.run {
            setOnClickListener {
                Log.d(TAG, "onClick()")

                (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(mAddEditText.windowToken, 0)
            }
        }

        Log.d(TAG, "token: ${mAddEditText.windowToken}")

        return rootView
    }
}
