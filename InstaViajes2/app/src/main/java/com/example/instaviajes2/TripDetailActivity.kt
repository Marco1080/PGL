package com.example.instaviajes2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TripDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_detail)

        val tripData = intent.getSerializableExtra("trip_data") as? HashMap<String, String>

        val titleTextView = findViewById<TextView>(R.id.tripDetailTitle)
        val locationTextView = findViewById<TextView>(R.id.tripDetailLocation)
        val dateTextView = findViewById<TextView>(R.id.tripDetailDate)
        val descriptionTextView = findViewById<TextView>(R.id.tripDetailDescription)

        if (tripData != null) {
            titleTextView.text = "🌍 " + tripData["title"]
            locationTextView.text = "📍 " + tripData["location"]
            dateTextView.text = "📅 " + tripData["date"]
            descriptionTextView.text = "📝 " + tripData["description"]
        }
    }
}
