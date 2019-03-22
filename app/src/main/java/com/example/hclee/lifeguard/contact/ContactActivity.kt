package com.example.hclee.lifeguard.contact

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.hclee.lifeguard.R
import kotlinx.android.synthetic.main.activity_contact.*

class ContactActivity : AppCompatActivity(), ContactContract.View {
    private val TAG: String = ContactActivity::class.java.simpleName

    override lateinit var presenter: ContactContract.Presenter

    private lateinit var mContext: Context
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
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

                presenter.refreshContactList()

                isRefreshing = false // After refresh, inform the view should not refresh anymore
            }
        }
        presenter = ContactPresenter(this, this)
        mLayoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        mRecyclerView = recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
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
