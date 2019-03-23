package com.example.hclee.lifeguard.contacts

import android.content.Context
import android.util.Log
import com.example.hclee.lifeguard.contacts.adapter.ContactsViewAdapter

/**
 * Created by hclee on 2019-03-19.
 */

interface ContactsLoadingFinishCallback {
    fun onContactsLoadingFinished()
}

class ContactsPresenter(private val mContext: Context, val mContactsView: ContactsContract.View): ContactsContract.Presenter {
    private val TAG: String = ContactsPresenter::class.java.simpleName
    private val mContactsList: ArrayList<ContactsData>
    private lateinit var mContactsTask: ContactsTask
    private lateinit var mContactsObserver: ContactsObserver
    lateinit var mAdapter: ContactsViewAdapter

    init {
        mContactsView.mPresenter = this
        mContactsList = ArrayList<ContactsData>()

        initializeContactsList()
    }

    override fun refreshContactsList() {
        Log.d(TAG, "refreshContactsList()")

        mContactsList.clear() // Clear all data of the list
        mContactsTask = ContactsTask(mContext, mContactsList, object: ContactsLoadingFinishCallback {
            override fun onContactsLoadingFinished() {
                setContactsViewAdapter()
            }
        })
        mContactsTask.execute()
    }

    private fun initializeContactsList() {
        Log.d(TAG, "initializeContactsList()")

        mContactsList.clear()
        mContactsTask = ContactsTask(mContext, mContactsList, object: ContactsLoadingFinishCallback {
            override fun onContactsLoadingFinished() {
                setContactsViewAdapter()
            }
        })
        mContactsTask.execute()

        Log.d(TAG, "contactsList.size = ${mContactsList.size}")
    }

    fun setContactsViewAdapter() {
        Log.d(TAG, "setContactsViewAdapter()")
        Log.d(TAG, "contactsList.size() = ${mContactsList.size}")

        mAdapter = ContactsViewAdapter(mContext, mContactsList)
        (mContactsView as ContactsActivity).mRecyclerView.adapter = mAdapter
    }
}
