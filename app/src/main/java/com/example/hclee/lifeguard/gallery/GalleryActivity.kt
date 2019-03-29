package com.example.hclee.lifeguard.gallery

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.hclee.lifeguard.AndroidThings
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.gallery.adapter.GalleryAdapter
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity(), GalleryContract.View {
    private val TAG: String = GalleryActivity::class.java.simpleName

    override lateinit var mPresenter: GalleryContract.Presenter

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: GalleryAdapter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mGlideRequestManager: RequestManager
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private val mListener: PictureLoadingListener = object: PictureLoadingListener {
        override fun onAllPictureLoadingFinish() {
            Toast.makeText(this@GalleryActivity, "All picture loaded", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onAllPictureLoadingFinish()")
        }

        override fun onPictureLoading(picture: Picture) {
            Log.d(TAG, "onPictureLoading()")

            mPresenter.addPictureToList(picture)
            mPresenter.updateAdapter(picture)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        init()

        pullPictureFromStorage()

        setResult(Activity.RESULT_OK)
    }

    private fun init() {
        mSwipeRefreshLayout = swipe_layout_gallery.apply {
            setOnRefreshListener {
                Log.d(TAG, "onRefresh()")

                isRefreshing = false
            }
        }
        mPresenter = GalleryPresenter(this)
        mGlideRequestManager = Glide.with(this)
        mAdapter = GalleryAdapter(this, mPresenter.getPictureList(), mGlideRequestManager)
        mLayoutManager = GridLayoutManager(this, 4)
        mRecyclerView = recycler_view_gallery.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            addItemDecoration(GridDividerDecoration(resources, R.drawable.divider_recycler_gallery))
            adapter = mAdapter
        }
    }

    fun pullPictureFromStorage() {
        mPresenter.pullPictureFromStorage(GalleryAndroidThings(GalleryTask(this, mListener)))
    }

    fun updateAdapter(picture: Picture) {
        mAdapter.notifyItemInserted(0) // Add newly pulled picture index 0
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume()")
    }
}
