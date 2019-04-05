package com.example.hclee.lifeguard.userprofile.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import com.example.hclee.lifeguard.R
import kotlinx.android.synthetic.main.fragment_medical_history.*

/**
 * Created by hclee on 2019-04-05.
 */

class MedicalHistoryFragment : Fragment() {
    private val TAG: String = MedicalHistoryFragment::class.java.simpleName

    private lateinit var mAddHistoryButton: Button
    private lateinit var mAddEditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_medical_history, container, false)

        mAddEditText = rootView.findViewById(R.id.et_add_history)
        mAddHistoryButton = rootView.findViewById(R.id.btn_add_medical_history)
        mAddHistoryButton.run {
            setOnClickListener {
                if(et_add_history.visibility == View.INVISIBLE) {
                    et_add_history.visibility = View.VISIBLE
                }
            }
        }

        return rootView
    }
}
