package com.example.hclee.lifeguard.gallery

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.gallery.adapter.GalleryAdapter
import com.example.hclee.lifeguard.gallery.listener.PictureLoadingListener
import kotlinx.android.synthetic.main.activity_gallery.*
import java.io.File

class GalleryActivity : AppCompatActivity(), GalleryContract.View {
    private val TAG: String = GalleryActivity::class.java.simpleName

    override lateinit var mPresenter: GalleryContract.Presenter

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: GalleryAdapter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mGlideRequestManager: RequestManager
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAndroidThings: GalleryAndroidThings
    private lateinit var mSharedPreferences: SharedPreferences
    lateinit var mToolbar: Toolbar
    private val mListener: PictureLoadingListener = object: PictureLoadingListener {
        override fun onAllPictureLoadingFinish() {
            Toast.makeText(this@GalleryActivity, "All picture loaded", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onAllPictureLoadingFinish()")
        }

        override fun onPictureLoading(picture: Picture) {
            Log.d(TAG, "onPictureLoading()")

            mPresenter.updateAdapter(picture)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        init()
        setSupportActionBar(mToolbar)
        pullPictureFromStorage()
    }

    private fun init() {
        mToolbar = (gallery_toolbar as Toolbar)
        mSharedPreferences = getSharedPreferences("picture_updated_preferences", MODE_PRIVATE)
        mSwipeRefreshLayout = swipe_layout_gallery.apply {
            setOnRefreshListener {
                Log.d(TAG, "onRefresh()")

                pullPictureFromStorage()

                isRefreshing = false
            }
        }
        mAndroidThings = GalleryAndroidThings(this)
        mPresenter = GalleryPresenter(this, mAndroidThings)
        mGlideRequestManager = Glide.with(this)
        mLayoutManager = GridLayoutManager(this, 4)
        mRecyclerView = recycler_view_gallery.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            addItemDecoration(GridDividerDecoration(resources, R.drawable.divider_recycler_gallery))
        }

        initAdapter()
    }

    private fun pullPictureFromStorage() {
        mPresenter.pullPictureFromStorage(GalleryTask(this, mListener))
    }

    override fun updateAdapter(picture: Picture) {
        Log.d(TAG, "updateAdapter()")

        mAdapter.notifyItemInserted(0) // Add newly pulled picture index 0

        Log.d(TAG, "size: ${mPresenter.getPictureList().size}")
    }

    /**
     * This function must run on main thread.
     * Otherwise, WrongThreadException occurs.
     * To make this function runs on main thread, PictureObserverManager passes handler to PictureObserver.
     * The handler handle onChange() on main thread.
     */
    private fun initAdapter() {
        mAdapter = GalleryAdapter(this, mPresenter.getPictureList(), mGlideRequestManager)
        mRecyclerView.adapter = mAdapter // This line must run on main thread
    }

    override fun notifyPictureUpdate() {
        Log.d(TAG, "notifyPictureUpdate()")


        initAdapter()

        setPictureUpdatePreference(true)
    }

    private fun setPictureUpdatePreference(preferences: Boolean) {
        val sharedPreferencesEditor: SharedPreferences.Editor = mSharedPreferences.edit()

        sharedPreferencesEditor.putBoolean("picture_updated", preferences)
        sharedPreferencesEditor.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onCreateOptionsMenu()")

        menuInflater.inflate(R.menu.toolbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menu_ok -> {
                mAdapter.getSelectedPicture().let {
                    makePictureProfileImage(it!!)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun makePictureProfileImage(picture: Picture) {
        Log.d(TAG, "makePictureProfileImage()")
        Log.d(TAG, "selected: ${picture.mPicturePath}")

        val picturePath: String = picture.mPicturePath
        val pictureUri: Uri = Uri.fromFile(File(picturePath))
        val resultIntent: Intent = Intent().apply {
            putExtra("picture_uri", pictureUri.toString())
        }

        setResult(Activity.RESULT_OK, resultIntent)
        onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        overridePendingTransition(R.anim.animation_slide_from_left, R.anim.animation_slide_to_right)
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart()")

        if(mSharedPreferences.getBoolean("picture_updated", false)) {
            Log.d(TAG, "picture updated")

            pullPictureFromStorage()

            setPictureUpdatePreference(false)
        }
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")
    }
}
