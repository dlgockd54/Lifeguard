package com.example.hclee.lifeguard.contacts

import com.example.hclee.lifeguard.AndroidThings

/**
 * Created by hclee on 2019-03-19.
 */

class ContactsPresenter(val mContactsView: ContactsContract.View, private val mAndroidThings: AndroidThings): ContactsContract.Presenter {
    private val TAG: String = ContactsPresenter::class.java.simpleName
    private val mContactsList: ArrayList<ContactsData>
    private val mContactsObserverManager: ContactsObserverManager

    init {
        mContactsList = ArrayList<ContactsData>()
        mContactsObserverManager = ContactsObserverManager((mAndroidThings as ContactsAndroidThings).mContext, this)
    }

    override fun refreshContactsList() {
        System.out.println("refreshContactsList()")

        mContactsList.clear() // Clear all data of the list
        mContactsView.getContactsTask().execute() // AsyncTask provided by view
    }

    override fun initializeContactsList() {
        System.out.println("initializeContactsList()")

        mContactsList.clear()
        mContactsView.getContactsTask().execute()

        System.out.println("initializeContactsList()")
    }

    override fun getContactsList(): ArrayList<ContactsData> {
        return mContactsList
    }

    override fun updateContactsViewAdapter() {
        System.out.println("updateContactsViewAdapter()")
        System.out.println("contactsList.size() = ${mContactsList.size}")

        mContactsView.updateContactsViewAdapter()
    }

    override fun notifyChange() {
        mContactsView.setIsNeedToUpdateContactsList(true)
    }
}
