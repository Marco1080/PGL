package com.example.instaviajes2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TripsActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AllTripsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips)

        recyclerView = findViewById(R.id.recyclerViewTrips)
        recyclerView.layoutManager = LinearLayoutManager(this)

        dbHelper = DataBaseHelper(this)
        val trips = dbHelper.getAllTrips().toMutableList()

        if (trips.isNotEmpty()) {
            adapter = AllTripsAdapter(trips) { position ->
                viewTrip(position, trips)
            }
            recyclerView.adapter = adapter
        } else {
            Toast.makeText(this, "No se encontraron viajes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewTrip(position: Int, trips: MutableList<Map<String, String>>) {
        val intent = Intent(this, TripDetailActivity::class.java)
        intent.putExtra("trip_data", HashMap(trips[position]))
        startActivity(intent)
    }
}
