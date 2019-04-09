package com.example.hclee.lifeguard.userprofile.fragment


import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

import com.example.hclee.lifeguard.R
import com.example.hclee.lifeguard.database.DatabaseManager
import com.example.hclee.lifeguard.database.listener.DatabaseObserverListener
import com.example.hclee.lifeguard.database.listener.MedicalHistoryObserverListener
import com.example.hclee.lifeguard.userprofile.*
import com.example.hclee.lifeguard.userprofile.adapter.MedicalHistoryAdapter
import com.example.hclee.lifeguard.userprofile.listener.MedicalHistoryLoadListener

/**
 * Created by hclee on 2019-04-05.
 */

class MedicalHistoryFragment : Fragment() {
    private val TAG: String = MedicalHistoryFragment::class.java.simpleName

    private lateinit var mAddHistoryButton: Button
    private lateinit var mLayout: ViewGroup
    private lateinit var mActivity: FragmentActivity
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mDividerItemDecoration: DividerItemDecoration
    private lateinit var mMedicalHistoryAdapter: MedicalHistoryAdapter
    private lateinit var mGlideRequestManager: RequestManager
    lateinit var mAddEditText: EditText
    lateinit var mAddButton: Button
    private val mMedicalHistoryObserverListener: DatabaseObserverListener = object: MedicalHistoryObserverListener {
        override fun onAdd(disease: String) {
            addToAdapter(MedicalHistory(disease))
        }

        override fun onEdit(disease: String) {

        }

        override fun onRemove(disease: String) {
            removeFromAdapter(disease)
        }

        override fun onChange() {

        }
    }
    private val mMedicalHistoryLoadListener: MedicalHistoryLoadListener = object: MedicalHistoryLoadListener {
        override fun onMedicalHistoryLoaded(medicalHistory: MedicalHistory) {
            Log.d(TAG, "onMedicalHistoryLoaded()")

            addToAdapter(medicalHistory)
        }

        override fun onAllMedicalHistoryLoaded() {
            Log.d(TAG, "onAllMedicalHistoryLoaded()")

            initAdapter()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_medical_history, container, false)

        Log.d(TAG, "onCreateView()")

        setHasOptionsMenu(true)

        init(rootView)
        DatabaseManager.registerObserverListener(mMedicalHistoryObserverListener)

        return rootView
    }

    private fun init(rootView: View) {
        mActivity = activity
        mAddEditText = rootView.findViewById(R.id.et_add_history)
        mAddHistoryButton = rootView.findViewById(R.id.btn_add_medical_history)
        mAddButton = rootView.findViewById(R.id.btn_add)
        mLayout = rootView.findViewById(R.id.layout_medical_history)
        pullMedicalHistoryFromDatabase()
        mLayoutManager = LinearLayoutManager(mActivity).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        mDividerItemDecoration = DividerItemDecoration(mActivity, (mLayoutManager as LinearLayoutManager).orientation)
        mRecyclerView = rootView.findViewById(R.id.recycler_view_medical_history)
        mRecyclerView.run {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            addItemDecoration(mDividerItemDecoration)
        }
        mGlideRequestManager = Glide.with(this)
        initAdapter()
        mAddHistoryButton.run {
            setOnClickListener {
                if(mAddEditText.visibility == View.INVISIBLE) {
                    mAddEditText.visibility = View.VISIBLE
                    mAddButton.visibility = View.VISIBLE
                }
            }
        }
        mAddButton.run {
            setOnClickListener {
                val disease: String = (mAddEditText.text).toString()
                val medicalHistory: MedicalHistory = MedicalHistory(disease)

                try {
                    (mActivity as UserProfileActivity).mPresenter.addMedicalHistoryToDatabse(medicalHistory)
                } catch(e: SQLiteConstraintException) {
                    Log.d(TAG, e.message)

                    Snackbar.make(mLayout, "We already have ${mAddEditText.text} in the list", Snackbar.LENGTH_LONG).apply {
                        setAction("확인") {
                            this.dismiss()
                        }
                        show()
                    }
                }

                mAddButton.visibility = View.INVISIBLE
                mAddEditText.visibility = View.INVISIBLE
                (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(mAddEditText.windowToken, 0)
            }
        }
        mLayout.run {
            setOnClickListener {
                Log.d(TAG, "onClick()")

                mAddButton.visibility = View.INVISIBLE
                mAddEditText.visibility = View.INVISIBLE
                (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(mAddEditText.windowToken, 0)
            }
        }
    }

    private fun pullMedicalHistoryFromDatabase() {
        Log.d(TAG, "pullMedicalHistoryFromDatabase")

        (mActivity as UserProfileActivity).mPresenter
            .pullMedicalHistoryFromDatabase(MedicalHistoryAndroidThings(
            MedicalHistoryTask((mActivity as UserProfileActivity), mMedicalHistoryLoadListener)))
    }

    private fun addToAdapter(medicalHistory: MedicalHistory) {
        Log.d(TAG, "addToAdapter()")

        (mActivity as UserProfileActivity).mPresenter.addMedicalHistoryToList(medicalHistory)
        mMedicalHistoryAdapter.notifyItemInserted(0)
    }

    private fun removeFromAdapter(disease: String) {
        Log.d(TAG, "removeFromAdapter()")

        (mActivity as UserProfileActivity).mPresenter.removeMedicalHistoryFromList(disease)
        initAdapter()
    }

    private fun initAdapter() {
        Log.d(TAG, "initAdapter()")

        mMedicalHistoryAdapter = MedicalHistoryAdapter((mActivity as UserProfileActivity),
            (mActivity as UserProfileActivity).mPresenter.getMedicalHistoryList(),
            mGlideRequestManager)
        mRecyclerView.adapter = mMedicalHistoryAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        Log.d(TAG,"onCreateOptionsMenu()")

        inflater?.inflate(R.menu.toolbar_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.d(TAG, "onOptionsItemSelected()")

        when(item?.itemId) {
            R.id.menu_edit -> {
                Log.d(TAG, "menu_edit selected")

                mMedicalHistoryAdapter.onEditMenuSelected()
            }
        }

        return true
    }
}
