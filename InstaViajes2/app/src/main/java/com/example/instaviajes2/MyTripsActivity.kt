package com.example.instaviajes2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyTripsActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TripsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_trips)

        recyclerView = findViewById(R.id.recycler_view_trips)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TripsAdapter(mutableListOf()) { position ->
            removeTrip(position)
        }
        recyclerView.adapter = adapter

        dbHelper = DataBaseHelper(this)
        val username = intent.getStringExtra("username")

        if (username == null) {
            Toast.makeText(this, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val trips = dbHelper.getTripsByUser(username).toMutableList()

        if (trips.isNotEmpty()) {
            recyclerView.post {
                adapter = TripsAdapter(trips) { position ->
                    removeTrip(position)
                }
                recyclerView.adapter = adapter
            }
        } else {
            Toast.makeText(this, "No se encontraron viajes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeTrip(position: Int) {
        adapter.removeItem(position)
    }
}
