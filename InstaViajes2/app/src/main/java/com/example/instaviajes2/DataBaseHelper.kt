package com.example.instaviajes2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(
    context: Context?,
    name: String? = "instaviajes2.db",
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 5
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

        val createTripsTable = """
            CREATE TABLE Trips (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                date TEXT NOT NULL,
                location TEXT NOT NULL,
                creatorUsername TEXT NOT NULL,
                FOREIGN KEY (creatorUsername) REFERENCES Users(username) ON DELETE CASCADE
            );
        """.trimIndent()

        val createCommentsTable = """
            CREATE TABLE Comments (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                tripId INTEGER NOT NULL,
                username TEXT NOT NULL,
                comment TEXT NOT NULL,
                date TEXT NOT NULL,
                FOREIGN KEY (tripId) REFERENCES Trips(id) ON DELETE CASCADE,
                FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
            );
        """.trimIndent()

        val createReportsTable = """
            CREATE TABLE Reports (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL,
                issue TEXT NOT NULL,
                date TEXT NOT NULL,
                status TEXT DEFAULT 'Pendiente',
                FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
            );
        """.trimIndent()

        db?.execSQL(createUsersTable)
        db?.execSQL(createTripsTable)
        db?.execSQL(createCommentsTable)
        db?.execSQL(createReportsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE Users ADD COLUMN email TEXT NOT NULL DEFAULT ''")
            db?.execSQL("ALTER TABLE Users ADD COLUMN phone TEXT NOT NULL DEFAULT ''")
        }
        if (oldVersion < 3) {
            val createTripsTable = """
                CREATE TABLE Trips (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    description TEXT NOT NULL,
                    date TEXT NOT NULL,
                    location TEXT NOT NULL,
                    creatorUsername TEXT NOT NULL,
                    FOREIGN KEY (creatorUsername) REFERENCES Users(username) ON DELETE CASCADE
                );
            """.trimIndent()
            db?.execSQL(createTripsTable)
        }
        if (oldVersion < 4) {
            val createCommentsTable = """
                CREATE TABLE Comments (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    tripId INTEGER NOT NULL,
                    username TEXT NOT NULL,
                    comment TEXT NOT NULL,
                    date TEXT NOT NULL,
                    FOREIGN KEY (tripId) REFERENCES Trips(id) ON DELETE CASCADE,
                    FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
                );
            """.trimIndent()
            db?.execSQL(createCommentsTable)
        }
        if (oldVersion < 5) {
            val createReportsTable = """
                CREATE TABLE Reports (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL,
                    issue TEXT NOT NULL,
                    date TEXT NOT NULL,
                    status TEXT DEFAULT 'Pendiente',
                    FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
                );
            """.trimIndent()
            db?.execSQL(createReportsTable)
        }
    }

    fun insertTrip(title: String, description: String, date: String, location: String, creatorUsername: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("title", title)
        values.put("description", description)
        values.put("date", date)
        values.put("location", location)
        values.put("creatorUsername", creatorUsername)
        return db.insert("Trips", null, values) != -1L
    }

    fun userExists(username: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Users WHERE username = ?", arrayOf(username))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun getTripsByUser(username: String): List<Map<String, String>> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Trips WHERE creatorUsername = ?", arrayOf(username))
        val trips = mutableListOf<Map<String, String>>()

        while (cursor.moveToNext()) {
            val trip = mapOf(
                "title" to cursor.getString(cursor.getColumnIndexOrThrow("title")),
                "location" to cursor.getString(cursor.getColumnIndexOrThrow("location")),
                "date" to cursor.getString(cursor.getColumnIndexOrThrow("date")),
                "description" to cursor.getString(cursor.getColumnIndexOrThrow("description"))
            )
            trips.add(trip)
        }
        cursor.close()
        return trips
    }

    fun insertReport(username: String, issue: String, date: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("issue", issue)
        values.put("date", date)
        return db.insert("Reports", null, values) != -1L
    }

    fun getAllTrips(): List<Map<String, String>> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Trips", null)
        val trips = mutableListOf<Map<String, String>>()

        while (cursor.moveToNext()) {
            val trip = mapOf(
                "title" to cursor.getString(cursor.getColumnIndexOrThrow("title")),
                "location" to cursor.getString(cursor.getColumnIndexOrThrow("location")),
                "date" to cursor.getString(cursor.getColumnIndexOrThrow("date")),
                "description" to cursor.getString(cursor.getColumnIndexOrThrow("description")),
                "creatorUsername" to cursor.getString(cursor.getColumnIndexOrThrow("creatorUsername"))
            )
            trips.add(trip)
        }
        cursor.close()
        return trips
    }

    fun getReports(username: String): List<Map<String, String>> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Reports WHERE username = ?", arrayOf(username))
        val reports = mutableListOf<Map<String, String>>()

        while (cursor.moveToNext()) {
            val report = mapOf(
                "issue" to cursor.getString(cursor.getColumnIndexOrThrow("issue")),
                "date" to cursor.getString(cursor.getColumnIndexOrThrow("date")),
                "status" to cursor.getString(cursor.getColumnIndexOrThrow("status"))
            )
            reports.add(report)
        }
        cursor.close()
        return reports
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
