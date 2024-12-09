package com.example.instaviajes.models

data class Viaje(
    val id: Int,
    val titulo: String,
    val autor: String,
    val pais: String,
    val fechaInicio: String,
    val fechaFin: String
)
