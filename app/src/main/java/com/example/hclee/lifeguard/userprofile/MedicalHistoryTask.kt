package com.example.hclee.lifeguard.userprofile

import android.content.Context
import android.database.Cursor
import android.os.AsyncTask
import com.example.hclee.lifeguard.database.DatabaseManager
import com.example.hclee.lifeguard.userprofile.listener.MedicalHistoryLoadListener

/**
 * Created by hclee on 2019-04-07.
 */

class MedicalHistoryTask(private val mContext: Context, private val mListener: MedicalHistoryLoadListener): AsyncTask<Unit, MedicalHistory, Unit>() {
    private val TAG: String = MedicalHistoryTask::class.java.simpleName

    override fun doInBackground(vararg params: Unit?) {
        val medicalHistoryCursor: Cursor = DatabaseManager.getMedicalHistoryCursor()

        while(medicalHistoryCursor.moveToNext()) {
            val disease: String = medicalHistoryCursor.getString(0)
            val medicalHistory: MedicalHistory = MedicalHistory(disease)

            publishProgress(medicalHistory)
        }

        medicalHistoryCursor.close()
    }

    override fun onProgressUpdate(vararg values: MedicalHistory?) {
        super.onProgressUpdate(*values)

        values[0]?.let {
            mListener.onMedicalHistoryLoaded(values[0]!!)
        }
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)

        mListener.onAllMedicalHistoryLoaded()
    }
}