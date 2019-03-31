package com.example.hclee.lifeguard.gallery.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.gallery.GalleryActivity
import com.example.hclee.lifeguard.gallery.GalleryContract
import com.example.hclee.lifeguard.gallery.Picture
import kotlinx.android.synthetic.main.picture_item.view.*

/**
 * Created by hclee on 2019-03-28.
 */

class GalleryAdapter(private val mActivity: GalleryContract.View, private val mPictureList: List<Picture>, private val mGlideRequestManager: RequestManager)
    : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    private val TAG: String = GalleryAdapter::class.java.simpleName
    private var mSelectedImageView: ImageView? = null
    private var mSelectedPicture: Picture? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GalleryViewHolder {
        Log.d(TAG, "onCreateViewHolder()")

        val view: View = (LayoutInflater.from(parent?.context).inflate(R.layout.picture_item, parent, false))
        val viewHolder: GalleryAdapter.GalleryViewHolder = GalleryViewHolder(view)

        return viewHolder
    }

    private fun clickPicture(imageView: ImageView, picture: Picture) {
        val mMenuOK: MenuItem = (mActivity as GalleryActivity).mToolbar.menu.getItem(0)

        if(imageView != mSelectedImageView) {
            mSelectedImageView.let {
                mSelectedImageView?.visibility = View.INVISIBLE
            }
        }

        if(imageView.visibility == View.VISIBLE) {
            imageView.visibility = View.INVISIBLE
            mSelectedPicture = null
            mSelectedImageView = null
            picture.isSelected = false
            mMenuOK.isVisible = false
        }
        else {
            imageView.visibility = View.VISIBLE
            mSelectedPicture = picture
            mSelectedImageView = imageView
            picture.isSelected = true
            mMenuOK.isVisible = true
        }
    }

    override fun getItemCount(): Int {
        return mPictureList.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder?, position: Int) {
        Log.d(TAG, "onBindViewHolder()")

        holder?.let {
            it.itemView.run {
                setOnClickListener {
                    Log.d(TAG, "onClick()")

                    clickPicture(it.iv_select, mPictureList[position])
                }
            }

            mGlideRequestManager
                .load(mPictureList[position].mPicturePath)
                .into(it.imageView)
        }
    }

    fun getSelectedPicture(): Picture? {
        return mSelectedPicture
    }

    class GalleryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_picture)
    }
}