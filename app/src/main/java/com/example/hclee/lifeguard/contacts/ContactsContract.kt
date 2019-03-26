package com.example.hclee.lifeguard.contacts

import android.content.Intent
import com.example.hclee.lifeguard.BasePresenter
import com.example.hclee.lifeguard.BaseView

/**
 * Created by hclee on 2019-03-19.
 */

interface ContactsContract {
    interface View: BaseView<Presenter> {
        fun setIsNeedToUpdateContactsList(need: Boolean)
        fun updateContactsViewAdapter()
        fun switchToAnotherActivity(intent: Intent)
    }

    interface Presenter: BasePresenter {
        fun refreshContactsList()
        fun notifyChange()
        fun getContactsList(): ArrayList<ContactsData>
    }
}
