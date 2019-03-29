package com.example.hclee.lifeguard.gallery.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.gallery.GalleryContract
import com.example.hclee.lifeguard.gallery.Picture

/**
 * Created by hclee on 2019-03-28.
 */

class GalleryAdapter(private val mActivity: GalleryContract.View, private val mPictureList: List<Picture>, private val mGlideRequestManager: RequestManager)
    : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    private val TAG: String = GalleryAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GalleryViewHolder {
        Log.d(TAG, "onCreateViewHolder()")

        val view: View = (LayoutInflater.from(parent?.context).inflate(R.layout.picture_item, parent, false))
        val viewHolder: GalleryAdapter.GalleryViewHolder = GalleryViewHolder(view).apply {
            itemView.run {
                setOnClickListener {
                    Log.d(TAG, "onClick()")
                }
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return mPictureList.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder?, position: Int) {
        Log.d(TAG, "onBindViewHolder()")

        holder?.let {
            mGlideRequestManager
                .load(mPictureList[position].mPicturePath)
                .into(it.imageView)
        }
    }

    class GalleryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_picture)
    }
}