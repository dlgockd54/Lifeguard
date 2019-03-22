package com.example.hclee.lifeguard.contact

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.hclee.lifeguard.contact.adapter.ContactViewAdapter

/**
 * Created by hclee on 2019-03-19.
 */

interface ContactLoadingFinishCallback {
    fun onContactLoadingFinished()
}

class ContactPresenter(private val mContext: Context, val contactView: ContactContract.View): ContactContract.Presenter {
    private val TAG: String = ContactPresenter::class.java.simpleName
    private val contactList: ArrayList<ContactData>
    private lateinit var contactTask: ContactTask
    lateinit var mAdapter: ContactViewAdapter

    init {
        contactView.presenter = this
        contactList = ArrayList<ContactData>()

        initializeContactList()
    }

    override fun refreshContactList() {
        Log.d(TAG, "refreshContactList()")

        contactList.clear() // Clear all data of the list
        contactTask = ContactTask(mContext, contactList, object: ContactLoadingFinishCallback {
            override fun onContactLoadingFinished() {
                setContactViewAdapter()
            }
        })
        contactTask.execute()
    }

    private fun initializeContactList() {
        Log.d(TAG, "initializeContactList()")

        contactList.clear()
        contactTask = ContactTask(mContext, contactList, object: ContactLoadingFinishCallback {
            override fun onContactLoadingFinished() {
                setContactViewAdapter()
            }
        })
        contactTask.execute()

        Log.d(TAG, "contactList.size = ${contactList.size}")
    }

    fun setContactViewAdapter() {
        Log.d(TAG, "setContactViewAdapter()")
        Log.d(TAG, "contactList.size() = ${contactList.size}")

        mAdapter = ContactViewAdapter(contactList)
        (contactView as ContactActivity).mRecyclerView.adapter = mAdapter
    }
}