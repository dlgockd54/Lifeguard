package com.example.hclee.lifeguard.contact.adapter

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
import com.example.hclee.lifeguard.contact.ContactData

/**
 * Created by hclee on 2019-03-19.
 */

class ContactViewAdapter(private val mContext: Context, private val contactList: List<ContactData>): RecyclerView.Adapter<ContactViewAdapter.ContactViewHolder>() {
    private val TAG: String = ContactViewAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContactViewAdapter.ContactViewHolder {
        Log.d(TAG, "onCreateViewHolder()")

        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.item, parent, false)
        val viewHolder: ContactViewAdapter.ContactViewHolder = ContactViewAdapter.ContactViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewAdapter.ContactViewHolder?, position: Int) {
        Log.d(TAG, "onBindViewHolder()")

        holder?.let {
            it.profileImageView.setImageDrawable(contactList[position].profileThumbnail)
            it.nameTextView.text = contactList[position].name
            it.phoneNumberTextView.text = contactList[position].phoneNumber
            it.itemView.setOnLongClickListener(object: View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    Log.d(TAG, "onLongClick()")
                    Log.d(TAG, "name: ${holder.nameTextView.text}, phoneNumber: ${holder.phoneNumberTextView.text}")

                    val telData: String = "tel: ${holder.phoneNumberTextView.text}"
                    val intent: Intent = Intent(Intent.ACTION_CALL)

                    intent.data = Uri.parse(telData)

                    try {
                        mContext.startActivity(intent)
                    } catch(e: SecurityException) {
                        e.printStackTrace()
                    }

                    return true
                }
            })
        }
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImageView: ImageView = itemView.findViewById(R.id.iv_profile)
        val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        val phoneNumberTextView: TextView = itemView.findViewById(R.id.tv_phone_number)
    }
}