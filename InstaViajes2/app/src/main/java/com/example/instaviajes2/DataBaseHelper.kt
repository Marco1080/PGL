package com.example.instaviajes2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(
    context: Context?,
    name: String? = "instaviajes2.db",
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 2
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createUsersTable = """
            CREATE TABLE Users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                email TEXT NOT NULL,
                phone TEXT NOT NULL
            );
        """.trimIndent()
        db?.execSQL(createUsersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE Users ADD COLUMN email TEXT NOT NULL DEFAULT ''")
            db?.execSQL("ALTER TABLE Users ADD COLUMN phone TEXT NOT NULL DEFAULT ''")
        }
    }

    fun userExists(username: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Users WHERE username = ?", arrayOf(username))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun insertUser(username: String, password: String, email: String, phone: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)
        values.put("email", email)
        values.put("phone", phone)
        return db.insert("Users", null, values) != -1L
    }
}
