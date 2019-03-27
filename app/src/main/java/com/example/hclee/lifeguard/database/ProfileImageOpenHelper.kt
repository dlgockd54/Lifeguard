package com.example.hclee.lifeguard.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract
import android.util.Log

/**
 * Created by hclee on 2019-03-27.
 */

class ProfileImageOpenHelper(private val mContext: Context, private val mName: String,
    private val mFactory: SQLiteDatabase.CursorFactory?, private val mVersion: Int): SQLiteOpenHelper(mContext, mName, mFactory, mVersion) {
    private val TAG: String = ProfileImageOpenHelper::class.java.simpleName
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(TAG, "onCreate()")

        val sql: String = "CREATE TABLE ProfileImage(phone_number text primary key, profile_image_uri text);"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "onUpgrade()")

        val sql: String = "DROP TABLE ${DatabaseManager.mTableName};"

        db?.execSQL(sql)
        onCreate(db)
    }
}