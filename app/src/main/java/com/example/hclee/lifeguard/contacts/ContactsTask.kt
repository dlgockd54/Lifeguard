package com.example.hclee.lifeguard.contacts

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.contacts.listener.ContactsLoadingFinishListener
import com.example.hclee.lifeguard.database.DatabaseManager
import com.example.hclee.lifeguard.database.LifeguardDatabaseOpenHelper

/**
 * Created by hclee on 2019-03-21.
 */

/**
 * Pull contacts using ContentProvider.
 * Update RecyclerView of ContactsActivity.
 */
class ContactsTask(private val mContext: Context, private val mContactsList: ArrayList<ContactsData>, private val callback: ContactsLoadingFinishListener)
    : AsyncTask<Void, Int, Unit>() {
    private val TAG: String = ContactsTask::class.java.simpleName
    private var cursorCount: Int = 0
    private lateinit var mDialog: CustomDialog
    private lateinit var mOpenHelper: LifeguardDatabaseOpenHelper

    override fun onPreExecute() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDialog = CustomDialog(mContext).apply {
                setCancelable(false)
                setContentView(R.layout.custom_dialog)
                create()
            }
        }

        mOpenHelper = LifeguardDatabaseOpenHelper(mContext, DatabaseManager.mDbName, null, 1)
        DatabaseManager.getDatabase(mOpenHelper)

        Log.d(TAG, "isShowing(): ${mDialog.isShowing}")

        super.onPreExecute()
    }

    private fun addEmergencyService() {
        var uri: Uri = Uri.parse("android.resource://${mContext.packageName}/${R.raw.emergency_119}")
        val cursor: Cursor = DatabaseManager.getProfileImageCursor("119")

        if(cursor.count < 1) {
            DatabaseManager.insertProfileImageUri("119", uri)
        }
        else {
            cursor.moveToFirst()
            uri = Uri.parse(cursor.getString(1))
        }

        mContactsList.add(ContactsData(uri, "119 안전신고센터", "119"))
    }

    /**
     * Only doInBackground() method runs on worker thread, others on main thread.
     */
    override fun doInBackground(vararg params: Void?) {
        val nameAndPhoneNumberCursor: Cursor = getNameAndPhoneNumberCursor()
        var progress: Int = 1

        addEmergencyService()

        cursorCount = nameAndPhoneNumberCursor.count
        Log.d(TAG, "cursor.count: ${cursorCount}")

        while(nameAndPhoneNumberCursor.moveToNext()) {
            var profileImageUri: Uri
            val name: String = nameAndPhoneNumberCursor.getString(0)
            val phoneNumber: String = nameAndPhoneNumberCursor.getString(1)
            val profileImageUriCursor: Cursor = DatabaseManager.getProfileImageCursor(phoneNumber)

            if(profileImageUriCursor.count < 1) { // Set default profile image
                Log.d(TAG, "profile image null!")

                // Insert default profile image
                DatabaseManager.insertProfileImageUri(phoneNumber, getDefaultProfileImageUri())
                profileImageUri = getDefaultProfileImageUri()
            }
            else {
                profileImageUriCursor.moveToNext()

                profileImageUri = Uri.parse(profileImageUriCursor.getString(1))
            }

            Log.d(TAG, "${nameAndPhoneNumberCursor.columnCount}")
            val contactsData: ContactsData = ContactsData(profileImageUri, name, phoneNumber)

            mContactsList.add(contactsData)

            publishProgress(progress++)
            profileImageUriCursor.close()
        }

        nameAndPhoneNumberCursor.close()
    }

    /**
     * Because of this function does not use SQLite but ContentResolver,
     * better exist in this class not DatabaseManager.
     */
    private fun getNameAndPhoneNumberCursor(): Cursor {
        val uri: Uri = android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection: Array<String> = arrayOf(
            android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            , android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER)
        val selection: String? = null // SQL WHERE clause
        val selectionArgs: Array<String>? = null
        val sortOrder: String = "${android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} COLLATE LOCALIZED ASC" // SQL ORDER BY clause
        val cursor: Cursor =  mContext.contentResolver.query(uri, // Extract contacts data from DB
            projection,
            selection,
            selectionArgs,
            sortOrder)

        return cursor
    }

    private fun getDefaultProfileImageUri(): Uri {
        val uri: Uri = Uri.parse("android.resource://${mContext.packageName}/${R.raw.default_profile}")

        Log.d(TAG, "uriString: ${uri.toString()}")

        return uri
    }

    // Update contacts loading progress as a dialog using progress bar
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

        Log.d(TAG, "Contacts data pulling done!")

        Toast.makeText(mContext, "Contacts data pulling done!", Toast.LENGTH_SHORT).show()

        mDialog.let {
            if (mDialog.isShowing) {
                mDialog.dismiss()
            }
        }

        callback.onContactsLoadingFinished()
    }
}
