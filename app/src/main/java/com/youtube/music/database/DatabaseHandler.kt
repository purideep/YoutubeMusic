package com.youtube.music.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.youtube.music.model.PlayListModel
import com.youtube.music.model.PlaylistVideo

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "YouTube"
        private val PLAY_LIST = "play_lists"
        private val PLAY_LIST_VIDEOS = "play_list_videos"

        private val OWNER_ID = "owner_id"
        private val PLAYLIST_ID = "play_list_id"
        private val DATA = "data"

        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {


        val playListTable = "CREATE TABLE ${PLAY_LIST}(" +
                "${KEY_ID} INTEGER PRIMARY KEY," +
                "${OWNER_ID} TEXT," +
                "${PLAYLIST_ID} TEXT," +
                "${DATA} TEXT)"

        val playListVideoTable = "CREATE TABLE ${PLAY_LIST_VIDEOS}(" +
                "${KEY_ID} INTEGER PRIMARY KEY," +
                "${OWNER_ID} TEXT," +
                "${PLAYLIST_ID} TEXT," +
                "${DATA} TEXT)"

        db?.execSQL(playListTable)
        db?.execSQL(playListVideoTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db?.execSQL("DROP TABLE IF EXISTS $PLAY_LIST")
        db?.execSQL("DROP TABLE IF EXISTS $PLAY_LIST_VIDEOS")
        onCreate(db)
    }

    fun addPlayListVideos(ownerId: String, playListId: String, list: List<PlaylistVideo>): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(OWNER_ID, ownerId)
        contentValues.put(PLAYLIST_ID, playListId)
        contentValues.put(DATA, Gson().toJson(list)) // EmpModelClass Phone
        val success = db.insert(PLAY_LIST_VIDEOS, null, contentValues)
        db.close() // Closing database connection
        return success
    }

    fun addPlayListModel(ownerId: String, model: PlayListModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(OWNER_ID, ownerId)
        contentValues.put(PLAYLIST_ID, model.id) // EmpModelClass Name
        contentValues.put(DATA, Gson().toJson(model)) // EmpModelClass Phone
        val success = db.insert(PLAY_LIST, null, contentValues)
        db.close() // Closing database connection
        return success
    }

    //method to read data
    fun getPlayLists(ownerId: String): List<PlayListModel> {
        val list = ArrayList<PlayListModel>()
        val selectQuery = "SELECT  * FROM $PLAY_LIST where $OWNER_ID = '$ownerId'"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        if (cursor.moveToFirst()) {
            do {
                val data = cursor.getString(cursor.getColumnIndex("data"))
                val model = Gson().fromJson(data, PlayListModel::class.java)
                list.add(model)
            } while (cursor.moveToNext())
        }
        return list
    }

    //method to read data
    fun getPlayListVideos(ownerId: String, playListId: String): List<PlaylistVideo> {
        val list = ArrayList<PlaylistVideo>()
        val selectQuery =
            "SELECT  * FROM $PLAY_LIST_VIDEOS where $OWNER_ID = '$ownerId' and play_list_id = '$playListId'"
//        val selectQuery =
//            "SELECT  * FROM $PLAY_LIST_VIDEOS where $OWNER_ID = '$ownerId'"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        if (cursor.moveToFirst()) {
            do {
                val data = cursor.getString(cursor.getColumnIndex("data"))
//                Log.e("Cursor Object", DatabaseUtils.dumpCursorToString(cursor))
                var myList: List<PlaylistVideo>
                val listType = object : TypeToken<List<PlaylistVideo>>() {}.type
                myList = Gson().fromJson(data, listType)
                list.addAll(myList)
            } while (cursor.moveToNext())
        }
        return list
    }

    /*
    //method to update data
    fun updateEmployee(emp: EmpModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName) // EmpModelClass Name
        contentValues.put(KEY_EMAIL, emp.userEmail) // EmpModelClass Email

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues, "id=" + emp.userId, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }*/

    //method to delete data
    fun deletePlayLists(ownerId: String): Int {
        val db = this.writableDatabase
        // Deleting Row
        val success = db.delete(PLAY_LIST, "$OWNER_ID='$ownerId'", null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun deletePlayListsVideo(ownerId: String): Int {
        val db = this.writableDatabase
        // Deleting Row
        val success = db.delete(PLAY_LIST_VIDEOS, "$OWNER_ID='$ownerId'", null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}