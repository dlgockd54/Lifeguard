package com.example.hclee.lifeguard.contacts.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.contacts.ContactsData

/**
 * Created by hclee on 2019-03-19.
 */

class ContactsViewAdapter(private val mContext: Context, private val mContactsList: List<ContactsData>): RecyclerView.Adapter<ContactsViewAdapter.ContactsViewHolder>() {
    private val TAG: String = ContactsViewAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContactsViewAdapter.ContactsViewHolder {
        Log.d(TAG, "onCreateViewHolder()")

        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.item, parent, false)
        val viewHolder: ContactsViewAdapter.ContactsViewHolder = ContactsViewAdapter.ContactsViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return mContactsList.size
    }

    override fun onBindViewHolder(holder: ContactsViewAdapter.ContactsViewHolder?, position: Int) {
        Log.d(TAG, "onBindViewHolder()")

        holder?.let {
            it.profileImageView.setImageDrawable(mContactsList[position].mProfileThumbnail)
            it.nameTextView.text = mContactsList[position].mName
            it.phoneNumberTextView.text = mContactsList[position].mPhoneNumber
            it.itemView.setOnLongClickListener(object: View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    Log.d(TAG, "onLongClick()")
                    Log.d(TAG, "name: ${holder.nameTextView.text}, phoneNumber: ${holder.phoneNumberTextView.text}")

                    val phoneNumber: String = (holder.phoneNumberTextView.text).toString()

                    callClickedPerson(phoneNumber)

                    return true
                }
            })
        }
    }

    private fun callClickedPerson(phoneNumber: String) {
        val telData: String = "tel: $phoneNumber"
        val intent: Intent = Intent(Intent.ACTION_CALL)

        intent.data = Uri.parse(telData)

        try {
            mContext.startActivity(intent)
        } catch(e: SecurityException) {
            e.printStackTrace()
        }
    }

    class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImageView: ImageView = itemView.findViewById(R.id.iv_profile)
        val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        val phoneNumberTextView: TextView = itemView.findViewById(R.id.tv_phone_number)
    }
}
