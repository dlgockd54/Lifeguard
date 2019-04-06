package com.example.hclee.lifeguard.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by hclee on 2019-03-27.
 */

class LifeguardDatabaseOpenHelper(private val mContext: Context, private val mName: String,
                                  private val mFactory: SQLiteDatabase.CursorFactory?, private val mVersion: Int): SQLiteOpenHelper(mContext, mName, mFactory, mVersion) {
    private val TAG: String = LifeguardDatabaseOpenHelper::class.java.simpleName
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(TAG, "onCreate()")

        val sqlArr: Array<String>
                = arrayOf("CREATE TABLE ${DatabaseManager.mProfileImageTableName}(phone_number text primary key, profile_image_uri text);",
            "CREATE TABLE ${DatabaseManager.mMedicalHistoryTableName}(disease text primary key);")

        for(sql in sqlArr) {
            db?.execSQL(sql)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "onUpgrade()")

        val sqlArr: Array<String>
        = arrayOf("DROP TABLE ${DatabaseManager.mProfileImageTableName};",
            "DROP TABLE ${DatabaseManager.mMedicalHistoryTableName};")

        for(sql in sqlArr) {
            db?.execSQL(sql)
        }

        onCreate(db)
    }
}