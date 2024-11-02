package com.example.practica_5_cuestionario_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Pregunta3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pregunta3)

        val siguienteBtn = findViewById<Button>(R.id.buttonSiguiente)
        val respuestaCorrecta = findViewById<RadioButton>(R.id.radioButtonCorrecto)

        siguienteBtn.setOnClickListener {
            val puntajeAnterior = intent.getIntExtra("puntaje", 0)
            val puntaje = puntajeAnterior + if (respuestaCorrecta.isChecked) 1 else 0

            val user = intent.getStringExtra("user")
            if (user == null) {
                Toast.makeText(this, "Error: el usuario es nulo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, Pregunta4::class.java)
            intent.putExtra("user", user)
            intent.putExtra("puntaje", puntaje)
            startActivity(intent)
        }
    }
}
