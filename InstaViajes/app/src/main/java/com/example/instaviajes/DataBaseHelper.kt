package com.example.instaviajes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(
    context: Context?,
    name: String? = "instaviajes.db",
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 1
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
            CREATE TABLE usuarios (
                idUsuario INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                apellidos TEXT,
                email TEXT,
                password TEXT
            )
            """
        )

        db?.execSQL(
            """
            CREATE TABLE viajes (
                idViaje INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                descripcion TEXT,
                fechaInicio TEXT NOT NULL,
                fechaFin TEXT NOT NULL,
                creadorId INTEGER NOT NULL,
                FOREIGN KEY(creadorId) REFERENCES usuarios(idUsuario)
            )
            """
        )

        db?.execSQL(
            """
            CREATE TABLE participaciones (
                idParticipacion INTEGER PRIMARY KEY AUTOINCREMENT,
                idUsuario INTEGER NOT NULL,
                idViaje INTEGER NOT NULL,
                FOREIGN KEY(idUsuario) REFERENCES usuarios(idUsuario),
                FOREIGN KEY(idViaje) REFERENCES viajes(idViaje),
                UNIQUE(idUsuario, idViaje)
            )
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS participaciones")
        db?.execSQL("DROP TABLE IF EXISTS viajes")
        db?.execSQL("DROP TABLE IF EXISTS usuarios")
        onCreate(db)
    }
}
