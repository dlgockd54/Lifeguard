package com.example.hclee.lifeguard.contact

import android.content.Context
import android.util.Log
import com.example.hclee.lifeguard.contact.adapter.ContactViewAdapter

/**
 * Created by hclee on 2019-03-19.
 */

class ContactPresenter(private val mContext: Context, contactView: ContactContract.View): ContactContract.Presenter {
    private val TAG: String = ContactPresenter::class.java.simpleName
    private val contactList: ArrayList<ContactData> = ArrayList<ContactData>()
    private val contactTask: ContactTask
    lateinit var mAdapter: ContactViewAdapter

    init {
        contactView.presenter = this
        contactTask = ContactTask(mContext, contactList)

        initializeContactList()
        setContactViewAdapter()
    }

    override fun showContactList() {
        Log.d(TAG, "showContactList()")

        contactList.let {

        }
    }

    private fun initializeContactList() {
        Log.d(TAG, "initializeContactList()")
        Log.d(TAG, "contactList.size = ${contactList.size}")

        contactTask.execute()

        Log.d(TAG, "contactList.size = ${contactList.size}")
    }

    private fun setContactViewAdapter() {
        Log.d(TAG, "setContactViewAdapter()")

        mAdapter = ContactViewAdapter(contactList)
    }
}