package com.nixie.projectku

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class dbhelpersqli(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_FULLNAME TEXT," +
                "$COLUMN_USERNAME TEXT," +
                "$COLUMN_TGL TEXT," +
                "$COLUMN_EMAIL TEXT," +
                "$COLUMN_PASSWORD TEXT," +
                "$COLUMN_NOHP INTEGER,"+
                "$COLUMN_ALAMAT TEXT," +
                "$COLUMN_jk TEXT)"
        db.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun isUserRegistered(email: String, username: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ? OR $COLUMN_USERNAME = ?"
        val cursor = db.rawQuery(query, arrayOf(email, username))
        val isRegistered = cursor.count > 0
        cursor.close()
        return isRegistered
    }

    fun insertUser(fullname: String,username: String,date:String ,email: String, password: String, nohp: Int,alamat: String,jk: String): Long {
        if (isUserRegistered(email, username)) {
            return -1
        }
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FULLNAME, fullname)
            put(COLUMN_USERNAME, username)
            put(COLUMN_TGL, date)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_NOHP, nohp)
            put(COLUMN_ALAMAT, alamat)
            put(COLUMN_jk, jk)

        }
        return db.insert(TABLE_NAME, null, values)
    }
    fun loginUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID),
            "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password),
            null, null, null
        )
        val isLoggedIn = cursor.count > 0
        cursor.close()
        return isLoggedIn
    }
    fun getDataByEmail(email: String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE email = ?", arrayOf(email))
    }
    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val imageResIds = listOf(R.drawable.img1, R.drawable.img4, R.drawable.img3)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val fullname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULLNAME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                // Memilih gambar secara acak
                val photoResId = imageResIds.random()
                userList.add(User(id, fullname, email, photoResId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }
    fun getAllUserNames(): List<String> {
        val userNamesList = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT $COLUMN_FULLNAME FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val fullname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULLNAME))
                userNamesList.add(fullname)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userNamesList
    }
    companion object {
        private const val DATABASE_NAME = "dbuser"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "tblusers"
        const val COLUMN_ID = "id"
        const val COLUMN_FULLNAME = "fullname"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_TGL = "tgl"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NOHP = "nohp"
        const val COLUMN_ALAMAT = "alamat"
        const val COLUMN_jk = "jk"
    }

}