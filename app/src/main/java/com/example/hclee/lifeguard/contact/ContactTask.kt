package com.example.hclee.lifeguard.contact

import android.content.Context
import android.database.Cursor
import android.database.Cursor.FIELD_TYPE_NULL
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.example.hclee.lifeguard.R

/**
 * Created by hclee on 2019-03-21.
 */

class ContactTask(private val mContext: Context, private val contactList: ArrayList<ContactData>, private val callback: ContactLoadingFinishCallback)
    : AsyncTask<Void, Int, Unit>() {
    private val TAG: String = ContactTask::class.java.simpleName
    private var cursorCount: Int = 0
    private lateinit var mDialog: CustomDialog

    override fun onPreExecute() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDialog = CustomDialog(mContext).apply {
                setCancelable(false)
                setContentView(R.layout.custom_dialog)
                create()
            }
        }

        mDialog.show()

        Log.d(TAG, "isShowing(): ${mDialog.isShowing}")

        super.onPreExecute()
    }

    /**
     * Only doInBackground() method runs on worker thread, others on main thread.
     */
    override fun doInBackground(vararg params: Void?) {
        val uri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection: Array<String> = arrayOf(
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI // Columns to extract
            , ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            , ContactsContract.CommonDataKinds.Phone.NUMBER)
        val selection: String? = null // SQL WHERE clause
        val selectionArgs: Array<String>? = null
        val sortOrder: String = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} COLLATE LOCALIZED ASC" // SQL ORDER BY clause
        var progress: Int = 1

        val cursor: Cursor =  mContext.contentResolver.query(uri, // Extract contact data from DB
            projection,
            selection,
            selectionArgs,
            sortOrder)

        cursorCount = cursor.count
        Log.d(TAG, "cursor.count: ${cursorCount}")

        while(cursor.moveToNext()) {
//            val profileThumbnail: Drawable =
            val drawableType: Int = cursor.getType(0)
            val name: String = cursor.getString(1)
            val phoneNumber: String = cursor.getString(2)
            val contactData: ContactData = ContactData(null, name, phoneNumber)

            Log.d(TAG, "${cursor.columnCount}")
            Log.d(TAG, "type: $drawableType, name: $name, phoneNumber: $phoneNumber")

            if (drawableType != FIELD_TYPE_NULL) { // If profile image has setted

            }

            contactList.add(contactData)

            publishProgress(progress++)
        }
    }

    // Update contact loading progress as a dialog using progress bar
    override fun onProgressUpdate(vararg values: Int?) {
        var progressValue: Int = 0

        values[0].let {
            progressValue = it?.times(100)!! / cursorCount
        }

        Log.d(TAG, "values[0]: ${values[0]}")
        Log.d(TAG, "progressValue: $progressValue")

        mDialog.let {
            mDialog.setProgress(progressValue)

            if (!mDialog.isShowing) {
                mDialog.show()
            }
        }
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)

        Log.d(TAG, "Contact data pulling done!")

        Toast.makeText(mContext, "Contact data pulling done!", Toast.LENGTH_SHORT).show()

        mDialog.let {
            if (mDialog.isShowing) {
                mDialog.dismiss()
            }
        }

        callback.onContactLoadingFinished()
    }
}