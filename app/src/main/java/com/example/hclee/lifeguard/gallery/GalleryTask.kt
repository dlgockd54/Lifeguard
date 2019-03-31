package com.example.hclee.lifeguard.gallery

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.provider.MediaStore
import android.util.Log
import com.example.hclee.lifeguard.gallery.listener.PictureLoadingListener

/**
 * Created by hclee on 2019-03-29.
 */

/**
 * Unlike ContactsTask, GalleryTask updates recycler view every time the task pull a picture.
 */
class GalleryTask(private val mContext: Context, private val mListener: PictureLoadingListener): AsyncTask<Void, Picture, Unit>() {
    private val TAG: String = GalleryTask::class.java.simpleName

    override fun doInBackground(vararg params: Void?) {
        val pictureCursor: Cursor = getPictureCursor()

        Log.d(TAG, "pictureCursor.count: ${pictureCursor.count}")

        while(pictureCursor.moveToNext()) {
            val picture: Picture = Picture(pictureCursor.getString(0),  pictureCursor.getString(1), false)

            publishProgress(picture)
        }
    }

    private fun getPictureCursor(): Cursor {
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection: Array<String> = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME)
        val selection: String? = null
        val selectionArgs: Array<String>? = null
        val sortOrder: String = "${MediaStore.MediaColumns.DATE_ADDED} COLLATE LOCALIZED DESC" // SQL ORDER BY clause
        val cursor: Cursor = mContext.contentResolver.query(uri,
            projection,
            selection,
            selectionArgs,
            sortOrder)

        return cursor
    }

    override fun onProgressUpdate(vararg values: Picture?) {
        super.onProgressUpdate(*values)

        values[0].let {
            mListener.onPictureLoading(values[0]!!)
        }
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)

        mListener.onAllPictureLoadingFinish()
    }
}