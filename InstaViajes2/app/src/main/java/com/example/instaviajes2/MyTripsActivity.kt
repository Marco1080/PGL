package com.example.instaviajes2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MyTripsActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_trips)

        dbHelper = DataBaseHelper(this)

        val username = intent.getStringExtra("username")

        if (username == null) {
            Toast.makeText(this, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val trips = dbHelper.getTripsByUser(username)

        if (trips.isNotEmpty()) {
            Log.d("MyTripsActivity", "Viajes encontrados para el usuario: $username")
            for ((index, trip) in trips.withIndex()) {
                Log.d("MyTripsActivity", "Viaje ${index + 1}:")
                Log.d("MyTripsActivity", "Título: ${trip["title"]}")
                Log.d("MyTripsActivity", "Ubicación: ${trip["location"]}")
                Log.d("MyTripsActivity", "Fecha: ${trip["date"]}")
                Log.d("MyTripsActivity", "Descripción: ${trip["description"]}")
                Log.d("MyTripsActivity", "-------------------------")
            }
        } else {
            Log.d("MyTripsActivity", "No se encontraron viajes para el usuario: $username")
        }
    }
}
