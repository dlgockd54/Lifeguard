package com.example.hclee.lifeguard.contact

import com.example.hclee.lifeguard.BasePresenter
import com.example.hclee.lifeguard.BaseView
import com.example.hclee.lifeguard.contact.adapter.ContactViewAdapter

/**
 * Created by hclee on 2019-03-19.
 */

interface ContactContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter {
        fun showContactList()
    }
}