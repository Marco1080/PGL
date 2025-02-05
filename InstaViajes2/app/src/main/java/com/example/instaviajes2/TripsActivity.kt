package com.example.instaviajes2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TripsActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = DataBaseHelper(this)

        val username = intent.getStringExtra("username")

        if (username == null) {
            Toast.makeText(this, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val trips = dbHelper.getTripsByUser(username)

        if (trips.isNotEmpty()) {
            println("Viajes encontrados para el usuario: $username")
            for ((index, trip) in trips.withIndex()) {
                println("Viaje ${index + 1}:")
                println("Título: ${trip["title"]}")
                println("Ubicación: ${trip["location"]}")
                println("Fecha: ${trip["date"]}")
                println("Descripción: ${trip["description"]}")
                println("-------------------------")
            }
        } else {
            println("No se encontraron viajes para el usuario: $username")
        }
    }
}
