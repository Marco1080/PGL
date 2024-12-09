package com.example.instaviajes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instaviajes.R
import com.example.instaviajes.models.Viaje

class ViajeAdapter(
    private val context: Context,
    private val viajes: List<Viaje>,
    private val onUnirseClick: (Int) -> Unit
) : RecyclerView.Adapter<ViajeAdapter.ViajeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViajeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_viaje, parent, false)
        return ViajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViajeViewHolder, position: Int) {
        val viaje = viajes[position]
        holder.tvTitulo.text = viaje.titulo
        holder.tvAutor.text = "Autor: ${viaje.autor}"
        holder.tvPais.text = "País: ${viaje.pais}"
        holder.tvFechas.text = "Fechas: ${viaje.fechaInicio} - ${viaje.fechaFin}"

        holder.btnUnirme.setOnClickListener {
            onUnirseClick(viaje.id)
        }
    }

    override fun getItemCount(): Int = viajes.size

    class ViajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val tvAutor: TextView = itemView.findViewById(R.id.tvAutor)
        val tvPais: TextView = itemView.findViewById(R.id.tvPais)
        val tvFechas: TextView = itemView.findViewById(R.id.tvFechas)
        val btnUnirme: Button = itemView.findViewById(R.id.btnUnirme)
    }
}
