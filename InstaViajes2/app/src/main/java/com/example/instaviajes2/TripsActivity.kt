package com.example.instaviajes2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TripsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tripsAdapter: TripsAdapter
    private val tripsList = mutableListOf<Trip>()  // Lista ficticia de viajes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Activa el diseño de pantalla completa sin recortes
        setContentView(R.layout.activity_trips)  // Configura el layout

        recyclerView = findViewById(R.id.recyclerViewTrips)
        recyclerView.layoutManager = LinearLayoutManager(this)

        tripsList.add(Trip("Madrid", "12/12/2024", "Viaje a Madrid para una conferencia de tecnología.", R.drawable.viajedevacaciones))
        tripsList.add(Trip("Barcelona", "15/12/2024", "Explorando la arquitectura de Gaudí.", R.drawable.viajedevacaciones))

        tripsAdapter = TripsAdapter(tripsList)
        recyclerView.adapter = tripsAdapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

data class Trip(val location: String, val date: String, val description: String, val imageResId: Int)

class TripsAdapter(private val trips: List<Trip>) : RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = trips[position]
        holder.bind(trip)
    }

    override fun getItemCount(): Int = trips.size

    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.imageViewTrip)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)
        private val dateTextView: TextView = itemView.findViewById(R.id.textViewDate)
        private val locationTextView: TextView = itemView.findViewById(R.id.textViewLocation)
        private val detailsButton: Button = itemView.findViewById(R.id.buttonDetails)

        fun bind(trip: Trip) {
            imageView.setImageResource(trip.imageResId)
            descriptionTextView.text = trip.description
            dateTextView.text = "Fecha: ${trip.date}"
            locationTextView.text = "Ubicación: ${trip.location}"

            detailsButton.setOnClickListener {
            }
        }
    }
}
