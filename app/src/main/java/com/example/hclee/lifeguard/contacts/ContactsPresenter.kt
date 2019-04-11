package com.example.hclee.lifeguard.contacts

import com.example.hclee.lifeguard.taskmanager.ContactsTaskManager
import com.example.hclee.lifeguard.taskmanager.TaskManager

/**
 * Created by hclee on 2019-03-19.
 */

class ContactsPresenter(val mContactsView: ContactsContract.View): ContactsContract.Presenter {
    private val TAG: String = ContactsPresenter::class.java.simpleName
    private val mContactsList: ArrayList<ContactsData> = ArrayList<ContactsData>()
    private val mTaskManager: TaskManager = ContactsTaskManager()

    override fun registerObserverManager(androidThings: ContactsAndroidThings) {
        ContactsObserverManager.registerObserver(androidThings, this@ContactsPresenter)
    }

    override fun refreshContactsList(androidThings: ContactsAndroidThings) {
        System.out.println("refreshContactsList()")

        mContactsList.clear() // Clear all data of the list
        mTaskManager.runTask(androidThings)
    }

    override fun initializeContactsList(androidThings: ContactsAndroidThings) {
        System.out.println("initializeContactsList()")

        mContactsList.clear()
        mTaskManager.runTask(androidThings)

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
