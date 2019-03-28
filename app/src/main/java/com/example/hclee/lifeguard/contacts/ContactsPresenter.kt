package com.example.hclee.lifeguard.contacts

import android.util.Log
import com.example.hclee.lifeguard.AndroidThings

/**
 * Created by hclee on 2019-03-19.
 */

interface ContactsLoadingFinishCallback {
    fun onContactsLoadingFinished()
}

class ContactsPresenter(val mContactsView: ContactsContract.View, private val mAndroidThings: AndroidThings): ContactsContract.Presenter {
    private val TAG: String = ContactsPresenter::class.java.simpleName
    private val mContactsList: ArrayList<ContactsData>
    private val mContactsObserverManager: ContactsObserverManager
    private val mCallback: ContactsLoadingFinishCallback = object: ContactsLoadingFinishCallback {
        override fun onContactsLoadingFinished() {
            updateContactsViewAdapter()
        }
    }
    private lateinit var mContactsTask: ContactsTask

    init {
        mContactsList = ArrayList<ContactsData>()
        mContactsObserverManager = ContactsObserverManager((mAndroidThings as ContactsAndroidThings).mContext, this)

        initializeContactsList()
    }

    override fun refreshContactsList() {
        Log.d(TAG, "refreshContactsList()")

        mContactsList.clear() // Clear all data of the list
        mContactsTask = ContactsTask((mAndroidThings as ContactsAndroidThings).mContext, mContactsList, mCallback)
        mContactsTask.execute()
    }

    private fun initializeContactsList() {
        Log.d(TAG, "initializeContactsList()")

        mContactsList.clear()
        mContactsTask = ContactsTask((mAndroidThings as ContactsAndroidThings).mContext, mContactsList, mCallback)
        mContactsTask.execute()

        Log.d(TAG, "contactsList.size = ${mContactsList.size}")
    }

    override fun getContactsList(): ArrayList<ContactsData> {
        return mContactsList
    }

    private fun updateContactsViewAdapter() {
        Log.d(TAG, "updateContactsViewAdapter()")
        Log.d(TAG, "contactsList.size() = ${mContactsList.size}")

        (mContactsView as ContactsActivity).updateContactsViewAdapter()
    }

    override fun notifyChange() {
        (mContactsView as ContactsActivity).setIsNeedToUpdateContactsList(true)
    }
}
