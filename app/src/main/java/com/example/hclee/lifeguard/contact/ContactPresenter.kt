package com.example.hclee.lifeguard.contact

import android.util.Log
import com.example.hclee.lifeguard.contact.adapter.ContactViewAdapter

/**
 * Created by hclee on 2019-03-19.
 */

class ContactPresenter(private val contactView: ContactContract.View): ContactContract.Presenter {
    private val TAG: String = ContactActivity::class.java.simpleName
    private lateinit var contactList: ArrayList<ContactData>
    lateinit var mAdapter: ContactViewAdapter

    init {
        contactView.presenter = this

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

        contactList = ArrayList<ContactData>()

        // contact list test
        for(i in 0..29) {
            contactList.add(ContactData(null, "AAA", "$i"))
        }

        Log.d(TAG, "contactList.size = ${contactList.size}")
    }

    private fun setContactViewAdapter() {
        Log.d(TAG, "setContactViewAdapter()")

        mAdapter = ContactViewAdapter(contactList)
    }
}