package com.example.hclee.lifeguard.contacts.adapter

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Message
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import com.daimajia.swipe.SwipeLayout
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.contacts.*
import com.example.hclee.lifeguard.editprofile.EditProfileActivity
import com.example.hclee.lifeguard.expandedprofile.ExpandedProfileActivity
import kotlinx.android.synthetic.main.item.view.*

/**
 * Created by hclee on 2019-03-19.
 */

class ContactsViewAdapter(private val mActivity: ContactsContract.View, private val mContactsList: List<ContactsData>,
                          private val mGlideRequestManager: RequestManager): RecyclerView.Adapter<ContactsViewAdapter.ContactsViewHolder>() {
    private val TAG: String = ContactsViewAdapter::class.java.simpleName
    private val KEY_INTENT_EXTRA: String = "phone_number"
    private val mSwipeListener: SwipeListenerImpl = SwipeListenerImpl()
    private val mViewHolderList: ArrayList<ContactsViewHolder>

    companion object {
        private val MSG_CLOSE_ITEM: Int = 345

        /**
         * Close all of items in this adapter, after startActivity()
         */
        class CloseItemHandler(private val contactsViewAdapter: ContactsViewAdapter) : Handler() {
            override fun handleMessage(msg: Message?) {
                when(msg?.what) {
                    MSG_CLOSE_ITEM -> {
                        contactsViewAdapter.closeAllItem()
                    }
                }
            }
        }
    }

    init {
        mViewHolderList = ArrayList<ContactsViewHolder>()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContactsViewAdapter.ContactsViewHolder {
        Log.d(TAG, "onCreateViewHolder()")

        val view: View = ((LayoutInflater.from(parent?.context).inflate(R.layout.item, parent, false)) as SwipeLayout)
        val viewHolder: ContactsViewAdapter.ContactsViewHolder = ContactsViewAdapter.ContactsViewHolder(view).apply {
            itemView.run {
                (this as SwipeLayout).addSwipeListener(mSwipeListener) // Add listener for swipe layout

                setOnClickListener {
                    closeAllItem()
                }

                setOnLongClickListener(object: View.OnLongClickListener {
                    override fun onLongClick(v: View?): Boolean {
                        Log.d(TAG, "onLongClick()")
                        Log.d(TAG, "name: ${nameTextView.text}, phoneNumber: ${phoneNumberTextView.text}")

                        val phoneNumber: String = (phoneNumberTextView.text).toString()

                        SMSSendManager.sendSMS(mActivity as Context, phoneNumber)
                        callClickedPerson(phoneNumber)

                        return true
                    }
                })
            }

            itemView.tv_edit_profile.setOnClickListener {
                Log.d(TAG, "tv_edit_profile click")

                moveEditProfileActivity((phoneNumberTextView.text).toString())
            }
        }

        mViewHolderList.add(viewHolder) // Everytime viewholder created, add the holder to mViewHolderList

        return viewHolder
    }

    override fun getItemCount(): Int {
        return mContactsList.size
    }

    override fun onBindViewHolder(holder: ContactsViewAdapter.ContactsViewHolder?, position: Int) {
        Log.d(TAG, "onBindViewHolder()")

        holder?.let {
            Log.d(TAG, mContactsList[position].mProfileThumbnailUri.toString())

            mGlideRequestManager
                .load(mContactsList[position].mProfileThumbnailUri)
                .circleCrop()
                .into(it.profileImageView)
            it.profileImageView.run {
                setOnClickListener {
                    Log.d(TAG, "onClick()")

                    moveExpandedProfileActivity(mContactsList[position].mProfileThumbnailUri)
                }
            }
            it.nameTextView.text = mContactsList[position].mName
            it.phoneNumberTextView.text = mContactsList[position].mPhoneNumber
        }
    }

    /**
     * Close all item what status is SwipeLayout.Status.OPEN
     */
    private fun closeAllItem() {
        Log.d(TAG, "closeAllItem()")

        for(viewHolder: ContactsViewHolder in mViewHolderList) {
            viewHolder.itemView.let {
                if((it as SwipeLayout).openStatus == SwipeLayout.Status.Open) {
                    it.close()
                }
            }
        }
    }

    private fun moveExpandedProfileActivity(profileThumbnailUri: Uri?) {
        val intent: Intent = Intent()
        val componentName: ComponentName = ComponentName((mActivity as ContactsActivity).applicationContext, ExpandedProfileActivity::class.java)

        intent.component = componentName

        profileThumbnailUri?.let {
            Log.d(TAG, "")
            intent.putExtra("profile_uri", it.toString())
        }

        mActivity.switchToAnotherActivity(intent)
    }

    private fun callClickedPerson(phoneNumber: String) {
        val telData: String = "tel: $phoneNumber"
        val intent: Intent = Intent(Intent.ACTION_CALL)

        intent.data = Uri.parse(telData)

        (mActivity as ContactsActivity).switchToAnotherActivity(intent)
    }

    private fun moveEditProfileActivity(phoneNumber: String) {
        val intent: Intent = Intent()
        val componentName: ComponentName = ComponentName((mActivity as ContactsActivity).applicationContext, EditProfileActivity::class.java)
        val message: Message = Message.obtain(null, MSG_CLOSE_ITEM)

        intent.component = componentName
        intent.putExtra(KEY_INTENT_EXTRA, phoneNumber)

        (mActivity as ContactsActivity).switchToAnotherActivity(intent)
        CloseItemHandler(this).sendMessageDelayed(message, 1000)
    }

    class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImageView: ImageView = itemView.findViewById(R.id.iv_profile)
        val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        val phoneNumberTextView: TextView = itemView.findViewById(R.id.tv_phone_number)
    }
}
