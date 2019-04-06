package com.example.hclee.lifeguard.userprofile.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.gallery.adapter.GalleryAdapter
import com.example.hclee.lifeguard.userprofile.MedicalHistory
import com.example.hclee.lifeguard.userprofile.UserProfileContract

/**
 * Created by hclee on 2019-04-06.
 */

class MedicalHistoryAdapter(private val mView: UserProfileContract.View, private val mMedicalHistoryList: List<MedicalHistory>)
    : RecyclerView.Adapter<MedicalHistoryAdapter.MedicalHistoryViewHolder>() {
    private val TAG: String = MedicalHistoryAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MedicalHistoryViewHolder {
        val view: View = (LayoutInflater.from(parent?.context).inflate(R.layout.medical_history_item, parent, false))
        val viewHolder: MedicalHistoryViewHolder = MedicalHistoryViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return mMedicalHistoryList.size
    }

    override fun onBindViewHolder(holder: MedicalHistoryViewHolder?, position: Int) {

    }

    class MedicalHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemView: TextView = itemView.findViewById(R.id.tv_disease)
    }
}