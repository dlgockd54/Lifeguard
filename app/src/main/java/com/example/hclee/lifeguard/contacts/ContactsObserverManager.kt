package com.example.hclee.lifeguard.contacts

import android.content.Context
import android.net.Uri

/**
 * Created by hclee on 2019-03-24.
 */

interface ContactsUpdateListener {
    fun onContactsUpdate()
}

class ContactsObserverManager(private val mContext: Context, private val mPresenter: ContactsPresenter) {
    private val mContactsObserver: ContactsObserver

    init {
        val uri: Uri = android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        mContactsObserver = ContactsObserver(null, object: ContactsUpdateListener {
            override fun onContactsUpdate() {
                mPresenter.notifyChange()
            }
        })

        mContext.contentResolver.registerContentObserver(uri, true, mContactsObserver)
    }
}