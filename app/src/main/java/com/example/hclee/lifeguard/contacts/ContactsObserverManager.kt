package com.example.hclee.lifeguard.contacts

import android.content.Context
import android.net.Uri
import com.example.hclee.lifeguard.contacts.listener.ContactsUpdateListener

/**
 * Created by hclee on 2019-03-24.
 */

object ContactsObserverManager {
    private lateinit var mContactsObserver: ContactsObserver
    private lateinit var mPresenter: ContactsPresenter

    fun registerObserver(androidThings: ContactsAndroidThings, presenter: ContactsPresenter) {
        val uri: Uri = android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        mPresenter = presenter
        mContactsObserver = ContactsObserver(null, object: ContactsUpdateListener {
            override fun onContactsUpdate() {
                mPresenter.notifyChange()
            }
        })

        androidThings.mContext.contentResolver.registerContentObserver(uri, true, mContactsObserver)
    }
}