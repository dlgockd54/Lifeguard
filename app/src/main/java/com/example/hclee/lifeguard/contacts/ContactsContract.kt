package com.example.hclee.lifeguard.contacts

import com.example.hclee.lifeguard.BasePresenter
import com.example.hclee.lifeguard.BaseView

/**
 * Created by hclee on 2019-03-19.
 */

interface ContactsContract {
    interface View: BaseView<Presenter> {
        fun setIsNeedToUpdateContactsList(need: Boolean)
    }

    interface Presenter: BasePresenter {
        fun refreshContactsList()
        fun notifyChange()
    }
}
