package com.example.hclee.lifeguard.userprofile.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.userprofile.MedicalHistory
import com.example.hclee.lifeguard.userprofile.UserProfileContract

/**
 * Created by hclee on 2019-04-06.
 */

class MedicalHistoryAdapter(private val mView: UserProfileContract.View, private val mMedicalHistoryList: List<MedicalHistory>,
                            private val mGlideRequestManager: RequestManager)
    : RecyclerView.Adapter<MedicalHistoryAdapter.MedicalHistoryViewHolder>() {
    private val TAG: String = MedicalHistoryAdapter::class.java.simpleName
    private val mViewHolderList: ArrayList<MedicalHistoryViewHolder> =  ArrayList<MedicalHistoryViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MedicalHistoryViewHolder {
        Log.d(TAG, "onCreateViewHolder()")

        val view: View = (LayoutInflater.from(parent?.context).inflate(R.layout.medical_history_item, parent, false))
        val viewHolder: MedicalHistoryViewHolder = MedicalHistoryViewHolder(view)

        mViewHolderList.add(viewHolder)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return mMedicalHistoryList.size
    }

    override fun onBindViewHolder(holder: MedicalHistoryViewHolder?, position: Int) {
        Log.d(TAG, "onBindViewHolder()")

        holder?.let {
            mGlideRequestManager
                .load(R.raw.disease)
                .circleCrop()
                .into(it.diseaseImageView)
            it.medicalHistoryView.text = mMedicalHistoryList[position].mDisease
            it.removeButton.setOnClickListener {
                removeViewHolder(holder)
            }
        }
    }

    private fun removeViewHolder(holder: MedicalHistoryViewHolder) {
        mViewHolderList.remove(holder)
        mView.mPresenter.removeMedicalHistoryFromDatabase(holder.medicalHistoryView.text.toString())

        if(itemCount == 0) {
            mViewHolderList.clear()
        }

        Log.d(TAG, "itemCount: $itemCount")
        Log.d(TAG, "viewholder size: ${mViewHolderList.size}")
    }

    fun onEditMenuSelected() {
        var value: Int = View.VISIBLE

        if(mViewHolderList.size > 0) {
            if(mViewHolderList[0].removeButton.visibility == View.VISIBLE) {
                value = View.INVISIBLE
            }
        }

        for(viewHolder in mViewHolderList) {
            viewHolder.removeButton.visibility = value
        }
    }

    class MedicalHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val diseaseImageView: ImageView = itemView.findViewById(R.id.iv_disease)
        val medicalHistoryView: TextView = itemView.findViewById(R.id.tv_disease)
        val removeButton: Button = itemView.findViewById(R.id.btn_remove_medical_history)
    }
}