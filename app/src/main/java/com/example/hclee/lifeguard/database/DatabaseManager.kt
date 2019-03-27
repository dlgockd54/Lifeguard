package com.example.hclee.lifeguard.database

import android.database.sqlite.SQLiteDatabase

/**
 * Created by hclee on 2019-03-27.
 */

/**
 * Database can be used only through this object.
 */
object DatabaseManager {
    private lateinit var mDb: SQLiteDatabase
    val mTableName: String = "ProfileImage"
    val mDbName: String = "profile_image.db"

    fun getDatabase(openHelper: ProfileImageOpenHelper): SQLiteDatabase {
        mDb = openHelper.writableDatabase

        return mDb
    }
}