package com.example.hclee.lifeguard.database

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

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

    fun getProfileImageUri(phoneNumber: String): Uri {
        val sql: String = "SELECT * FROM $mTableName WHERE phone_number = '$phoneNumber'"
        val cursor: Cursor = rawQuery(sql, null)
        var profileImageUriString: String = ""

        if(cursor.count > 0) {
            cursor.moveToNext() // Move cursor to first row

            profileImageUriString = cursor.getString(1)
        }

        cursor.close()

        return Uri.parse(profileImageUriString)
    }

    fun setProfileImageUri(phoneNumber: String, uri: Uri) {
        val sql: String = "UPDATE $mTableName SET profile_image_uri = '${uri.toString()}' WHERE phone_number = '$phoneNumber'"

        mDb.execSQL(sql)
    }

    private fun rawQuery(sql: String, selectionArgs: Array<String>?): Cursor {
        return mDb.rawQuery(sql, selectionArgs)
    }
}