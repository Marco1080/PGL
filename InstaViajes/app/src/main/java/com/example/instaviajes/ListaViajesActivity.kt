package com.example.instaviajes

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class ListaViajesActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_viajes)

        dbHelper = DataBaseHelper(this)

        listarViajes()
    }

    private fun listarViajes() {
        val db: SQLiteDatabase = dbHelper.readableDatabase

        val query = """
            SELECT viajes.idViaje, 
                   viajes.titulo, 
                   usuarios.nombre || ' ' || usuarios.apellidos AS creadorNombre, 
                   viajes.pais, 
                   viajes.fechaInicio, 
                   viajes.fechaFin
            FROM viajes
            INNER JOIN usuarios ON viajes.creadorId = usuarios.idUsuario
        """

        try {
            val cursor = db.rawQuery(query, null)

            while (cursor.moveToNext()) {
                val idViaje = cursor.getInt(cursor.getColumnIndexOrThrow("idViaje"))
                val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
                val creadorNombre = cursor.getString(cursor.getColumnIndexOrThrow("creadorNombre"))
                val pais = cursor.getString(cursor.getColumnIndexOrThrow("pais"))
                val fechaInicio = cursor.getString(cursor.getColumnIndexOrThrow("fechaInicio"))
                val fechaFin = cursor.getString(cursor.getColumnIndexOrThrow("fechaFin"))

                Log.d("ListaViajes", "ID: $idViaje, Título: $titulo, Creador: $creadorNombre, País: $pais, Inicio: $fechaInicio, Fin: $fechaFin")
            }

            cursor.close()
        } catch (e: Exception) {
            Log.e("ListaViajes", "Error al listar los viajes: ${e.message}")
        }
    }
}
