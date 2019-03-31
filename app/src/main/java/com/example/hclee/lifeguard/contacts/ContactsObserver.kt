package com.example.hclee.lifeguard.contacts

import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.util.Log
import com.example.hclee.lifeguard.contacts.listener.ContactsUpdateListener

/**
 * Created by hclee on 2019-03-23.
 */

class ContactsObserver(handler: Handler?, private val mCallback: ContactsUpdateListener): ContentObserver(handler) {
    private val TAG: String = ContactsObserver::class.java.simpleName

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)

        Log.d(TAG, "onChange()")
        Log.d(TAG, "uri: $uri")

        mCallback.onContactsUpdate()
    }
}
