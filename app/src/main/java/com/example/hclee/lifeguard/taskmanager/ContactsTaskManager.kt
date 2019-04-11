package com.example.hclee.lifeguard.taskmanager

import com.example.hclee.lifeguard.AndroidThings
import com.example.hclee.lifeguard.contacts.ContactsAndroidThings

/**
 * Created by hclee on 2019-04-11.
 */

/**
 * Run AsyncTask instead of presenter.
 */
class ContactsTaskManager: TaskManager {
    override fun runTask(androidThings: AndroidThings) {
        (androidThings as ContactsAndroidThings).mTask.execute()
    }
}