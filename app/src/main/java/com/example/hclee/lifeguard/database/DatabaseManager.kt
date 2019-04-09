package com.example.hclee.lifeguard.database

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log
import com.example.hclee.lifeguard.database.listener.DatabaseObserverListener
import com.example.hclee.lifeguard.database.listener.MedicalHistoryObserverListener
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
    val mProfileImageTableName: String = "ProfileImage"
    val mDbName: String = "lifeguard.db"
    val mMedicalHistoryTableName: String = "MedicalHistory"
    private val mObserverListenerList: LinkedList<DatabaseObserverListener> = LinkedList<DatabaseObserverListener>()

    fun getDatabase(openHelper: LifeguardDatabaseOpenHelper): SQLiteDatabase {
        mDb = openHelper.writableDatabase

        return mDb
    }

    fun registerObserverListener(listener: DatabaseObserverListener) {
        Log.d(TAG, "registerObserver")

        mObserverListenerList.add(listener)
    }

    fun unregisterObserverListener(listener: DatabaseObserverListener) {
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
        val sql: String = "SELECT * FROM $mProfileImageTableName WHERE phone_number = '$phoneNumber'"
        val cursor: Cursor = rawQuery(sql, null)

        return cursor
    }

    fun updateProfileImageUri(phoneNumber: String, uri: Uri) {
        val sql: String = "UPDATE $mProfileImageTableName SET profile_image_uri = '${uri.toString()}' WHERE phone_number = '$phoneNumber'"

        mDb.execSQL(sql)
        notifyChangeToObserverListener(Change.EDIT, uri)
    }

    fun insertProfileImageUri(phoneNumber: String, uri: Uri) {
        val sql: String = "INSERT INTO $mProfileImageTableName VALUES ('$phoneNumber', '${uri.toString()}')"

        mDb.execSQL(sql)
        notifyChangeToObserverListener(Change.ADD, uri)
    }

    private fun notifyChangeToObserverListener(notify: Change, change: Any) {
        Log.d(TAG, "notifyChangeToObserverListener")

        for(observer in mObserverListenerList) {
            if(observer is MedicalHistoryObserverListener) {
                when(notify) {
                    Change.ADD -> {
                        observer.onAdd(change as String)
                    }
                    Change.EDIT -> {
                        observer.onEdit(change as String)
                    }
                    Change.REMOVE -> {
                        observer.onRemove(change as String)
                    }
                }
            }
            else {
                observer.onChange()
            }
        }
    }

    fun insertMedicalHistory(disease: String) {
        val sql: String = "INSERT INTO $mMedicalHistoryTableName VALUES ('$disease')"

        mDb.execSQL(sql)
        notifyChangeToObserverListener(Change.ADD, disease)
    }

    fun getMedicalHistoryCursor(): Cursor {
        val sql: String = "SELECT * FROM $mMedicalHistoryTableName"
        val cursor: Cursor = rawQuery(sql, null)

        return cursor
    }

    fun removeMedicalHistory(disease: String) {
        val sql: String = "DELETE FROM $mMedicalHistoryTableName WHERE disease = '$disease'"

        mDb.execSQL(sql)
        notifyChangeToObserverListener(Change.REMOVE, disease)
    }

    private fun rawQuery(sql: String, selectionArgs: Array<String>?): Cursor {
        return mDb.rawQuery(sql, selectionArgs)
    }
}