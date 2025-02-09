package com.example.instaviajes2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView

class AllTripsAdapter(private var trips: MutableList<Map<String, String>>, private val onView: (Int) -> Unit)
    : RecyclerView.Adapter<AllTripsAdapter.TripViewHolder>() {

    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.trip_title)
        val location: TextView = itemView.findViewById(R.id.trip_location)
        val date: TextView = itemView.findViewById(R.id.trip_date)
        val description: TextView = itemView.findViewById(R.id.trip_description)
        val viewButton: Button = itemView.findViewById(R.id.trip_view_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_card, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = trips[position]
        holder.title.text = "🌍 " + trip["title"]
        holder.location.text = "📍 " + trip["location"]
        holder.date.text = "📅 " + trip["date"]
        holder.description.text = "📝 " + trip["description"]

        holder.viewButton.setOnClickListener {
            onView(position)
        }
    }

    override fun getItemCount(): Int = trips.size
}
