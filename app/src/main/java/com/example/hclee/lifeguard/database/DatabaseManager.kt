package com.example.hclee.lifeguard.database

import android.database.Cursor
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

    fun getProfileImageUriString(phoneNumber: String): String {
        val sql: String = "SELECT * FROM $mTableName WHERE phone_number = '$phoneNumber'"
        val cursor: Cursor = rawQuery(sql, null)
        var profileImageUriString: String = ""

        if(cursor.count > 0) {
            cursor.moveToNext() // Move cursor to first row

            profileImageUriString = cursor.getString(1)
        }

        cursor.close()

        return profileImageUriString
    }

    private fun rawQuery(sql: String, selectionArgs: Array<String>?): Cursor {
        return mDb.rawQuery(sql, selectionArgs)
    }
}