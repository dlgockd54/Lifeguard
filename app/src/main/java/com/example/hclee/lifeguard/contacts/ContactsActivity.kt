package com.example.hclee.lifeguard.contacts

import android.content.ComponentName
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.contacts.adapter.ContactsViewAdapter
import com.example.hclee.lifeguard.contacts.listener.ContactsLoadingFinishListener
import com.example.hclee.lifeguard.database.DatabaseManager
import com.example.hclee.lifeguard.editprofile.listener.EditProfileObserverListener
import com.example.hclee.lifeguard.userprofile.UserProfileActivity
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.content_contacts.*

class ContactsActivity : AppCompatActivity(), ContactsContract.View {
    private val TAG: String = ContactsActivity::class.java.simpleName

    override lateinit var mPresenter: ContactsContract.Presenter

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mDividerItemDecoration: DividerItemDecoration
    private var mIsNeedToUpdateContactsList: Boolean = false
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ContactsViewAdapter
    private lateinit var mGlideRequestManager: RequestManager
    private lateinit var mAndroidThings: ContactsAndroidThings
    private lateinit var mToolbar: Toolbar
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView
    private val mEditProfileObserverListener: EditProfileObserverListener = object: EditProfileObserverListener {
        override fun onChange() {
            setIsNeedToUpdateContactsList(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        Log.d(TAG, "onCreate()")

        init()
        DatabaseManager.registerObserverListener(mEditProfileObserverListener) // Register listener, watches changes on database

        mPresenter.initializeContactsList()
    }

    private fun setToolbar() {
        mToolbar = (contacts_toolbar as Toolbar)
        setSupportActionBar(mToolbar)

        // Set left button on toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
    }

    private fun init() {
        setToolbar()
        mDrawerLayout = drawer_layout
        mNavigationView = navigation_view.apply {
            setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    Log.d(TAG, "onNavigationItemSelected()")

                    when(item.itemId) {
                        R.id.sms_setting -> {
                            Log.d(TAG, "sms_setting")
                            val intent: Intent = Intent(applicationContext, UserProfileActivity::class.java)

                            startActivity(intent)
                            overridePendingTransition(R.anim.animation_slide_from_right, R.anim.animation_slide_to_left)
                        }
                    }

                    mDrawerLayout.closeDrawers() // Close drawer

                    return true
                }
            })
        }
        mSwipeRefreshLayout = swipe_layout.apply {
            setOnRefreshListener{
                Log.d(TAG, "onRefresh()")

                mPresenter.refreshContactsList()

                isRefreshing = false // After refresh, inform the view should not refresh anymore
            }
        }
        mAndroidThings = ContactsAndroidThings(this)
        mPresenter = ContactsPresenter(this, mAndroidThings)
        mLayoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        mDividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation)
        mRecyclerView = recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            addItemDecoration(mDividerItemDecoration)
        }
        mGlideRequestManager = Glide.with(this)
    }

    override fun getContactsTask(): ContactsTask {
        return ContactsTask(this, mPresenter.getContactsList(), object: ContactsLoadingFinishListener {
            override fun onContactsLoadingFinished() {
                mPresenter.updateContactsViewAdapter()
            }
        })
    }

    override fun updateContactsViewAdapter() {
        mAdapter = ContactsViewAdapter(this, mPresenter.getContactsList(), mGlideRequestManager)
        mRecyclerView.adapter = mAdapter
    }

    override fun setIsNeedToUpdateContactsList(need: Boolean) {
        mIsNeedToUpdateContactsList = need
    }

    override fun switchToAnotherActivity(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.animation_slide_from_right, R.anim.animation_slide_to_left) // Activity slide animation
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START) // Open drawer, if left button on toolbar clicked
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) { // If drawer is opened close drawer
            mDrawerLayout.closeDrawers()
        }
        else { // Otherwise
            super.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume()")

        if(mIsNeedToUpdateContactsList) {
            mPresenter.refreshContactsList()

            setIsNeedToUpdateContactsList(false)
        }
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

        DatabaseManager.unregisterObserverListener(mEditProfileObserverListener)
    }
}
