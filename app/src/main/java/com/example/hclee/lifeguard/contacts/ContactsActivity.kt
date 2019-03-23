package com.example.hclee.lifeguard.contacts

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.hclee.lifeguard.R
import kotlinx.android.synthetic.main.activity_contact.*

class ContactsActivity : AppCompatActivity(), ContactsContract.View {
    private val TAG: String = ContactsActivity::class.java.simpleName

    override lateinit var mPresenter: ContactsContract.Presenter

    private lateinit var mContext: Context
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mDividerItemDecoration: DividerItemDecoration
    lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        Log.d(TAG, "onCreate()")

        init()
    }

    private fun init() {
        mContext = applicationContext
        mSwipeRefreshLayout = swipe_layout.apply {
            setOnRefreshListener{
                Log.d(TAG, "onRefresh()")

                mPresenter.refreshContactsList()

                isRefreshing = false // After refresh, inform the view should not refresh anymore
            }
        }
        mPresenter = ContactsPresenter(this, this)
        mLayoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        mDividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation)
        mRecyclerView = recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            addItemDecoration(mDividerItemDecoration)
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart()")
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
