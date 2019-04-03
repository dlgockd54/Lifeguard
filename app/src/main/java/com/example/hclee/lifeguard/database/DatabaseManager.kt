package com.example.hclee.lifeguard.database

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log
import com.example.hclee.lifeguard.editprofile.listener.EditProfileObserverListener
import java.util.*

/**
 * Created by hclee on 2019-03-27.
 */

/**
 * Database can be used only through this object.
 */
object DatabaseManager {
    private val TAG: String = DatabaseManager::class.java.simpleName
    private lateinit var mDb: SQLiteDatabase
    val mTableName: String = "ProfileImage"
    val mDbName: String = "profile_image.db"
    private val mObserverListenerList: LinkedList<EditProfileObserverListener> = LinkedList<EditProfileObserverListener>()

    fun getDatabase(openHelper: ProfileImageOpenHelper): SQLiteDatabase {
        mDb = openHelper.writableDatabase

        return mDb
    }

    fun registerObserverListener(listener: EditProfileObserverListener) {
        Log.d(TAG, "registerObserver")

        mObserverListenerList.add(listener)
    }

    fun unregisterObserverListener(listener: EditProfileObserverListener) {
        mObserverListenerList.remove(listener)
    }

    fun getProfileImageUri(phoneNumber: String): Uri {
        val cursor: Cursor = getProfileImageCursor(phoneNumber)
        var profileImageUriString: String = ""

        if(cursor.count > 0) {
            cursor.moveToNext() // Move cursor to first row

            profileImageUriString = cursor.getString(1)
        }

        cursor.close()

        return Uri.parse(profileImageUriString)
    }

    fun getProfileImageCursor(phoneNumber: String): Cursor {
        val sql: String = "SELECT * FROM $mTableName WHERE phone_number = '$phoneNumber'"
        val cursor: Cursor = rawQuery(sql, null)

        return cursor
    }

    fun updateProfileImageUri(phoneNumber: String, uri: Uri) {
        val sql: String = "UPDATE $mTableName SET profile_image_uri = '${uri.toString()}' WHERE phone_number = '$phoneNumber'"

        mDb.execSQL(sql)
        notifyChangeToObserverListener()
    }

    fun insertProfileImageUri(phoneNumber: String, uri: Uri) {
        val sql: String = "INSERT INTO ${DatabaseManager.mTableName} VALUES ('$phoneNumber', '${uri.toString()}')"

        mDb.execSQL(sql)
        notifyChangeToObserverListener()
    }

    private fun notifyChangeToObserverListener() {
        Log.d(TAG, "notifyChangeToObserverListener")

        for(observer in mObserverListenerList) {
            observer.onChange()
        }
    }

    private fun rawQuery(sql: String, selectionArgs: Array<String>?): Cursor {
        return mDb.rawQuery(sql, selectionArgs)
    }
}