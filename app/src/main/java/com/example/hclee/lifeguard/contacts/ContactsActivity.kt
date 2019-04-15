package com.example.hclee.lifeguard.contacts

import android.content.Intent
import android.graphics.Color
import android.os.Build
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
import com.example.hclee.lifeguard.database.listener.DatabaseObserverListener
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
    private lateinit var mToolbar: Toolbar
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView
    private val mEditProfileObserverListener: DatabaseObserverListener = object:
        DatabaseObserverListener {
        override fun onChange() {
            setIsNeedToUpdateContactsList(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        Log.d(TAG, "onCreate()")

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.statusBarColor = Color.rgb(255, 45, 48)
//        }

        init()

        mPresenter.registerObserverManager(getAndroidThings()) // Register observer, watches contacts changes
        DatabaseManager.registerObserverListener(mEditProfileObserverListener) // Register listener, watches changes on database
        mPresenter.initializeContactsList(getAndroidThings())
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

                mPresenter.refreshContactsList(getAndroidThings())

                isRefreshing = false // After refresh, inform the view should not refresh anymore
            }
        }
        mPresenter = ContactsPresenter(this/* , getAndroidThings() */)
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

    /**
     * Allocate ContactsTask, then return it.
     */
    private fun getContactsTask(): ContactsTask {
        return ContactsTask(this, mPresenter.getContactsList(), object: ContactsLoadingFinishListener {
            override fun onContactsLoadingFinished() {
                mPresenter.updateContactsViewAdapter()
            }
        })
    }

    /**
     * AsyncTask extended object can't be reused.
     * Every moment AsyncTask object used return task object included android things.
     */
    private fun getAndroidThings(): ContactsAndroidThings {
        return ContactsAndroidThings(this, getContactsTask())
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
            mPresenter.refreshContactsList(getAndroidThings())

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
