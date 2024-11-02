package com.example.practica_5_cuestionario_2

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Pregunta5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pregunta5)

        val terminarBtn = findViewById<Button>(R.id.buttonTerminar)
        val respuestaCorrecta = findViewById<RadioButton>(R.id.radioButtonCorrecto)

        terminarBtn.setOnClickListener {
            val puntajeAnterior = intent.getIntExtra("puntaje", 0)
            val puntajeFinal = puntajeAnterior + if (respuestaCorrecta.isChecked) 1 else 0
            val user = intent.getStringExtra("user")

            // Verificar que 'user' no sea nulo
            if (user.isNullOrEmpty()) {
                Toast.makeText(this, "Error: el usuario es nulo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbHelper = AdminSQLiteOpenHelper(this, "bd_usuarios", null, 1)
            val db = dbHelper.writableDatabase

            // Realizar la consulta solo si 'user' es válido
            val cursor = db.rawQuery("SELECT nota FROM usuarios WHERE user = ?", arrayOf(user))

            if (cursor.moveToFirst()) {
                val notaActual = cursor.getInt(0)
                if (puntajeFinal > notaActual) {
                    val values = ContentValues().apply {
                        put("nota", puntajeFinal)
                    }
                    db.update("usuarios", values, "user = ?", arrayOf(user))
                    Toast.makeText(this, "¡Nueva nota guardada!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Usuario no encontrado en la base de datos", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
            db.close()

            val intent = Intent(this, PerfilUsuario::class.java)
            intent.putExtra("user", user)
            intent.putExtra("nota", puntajeFinal)
            startActivity(intent)
            finish()
        }
    }
}
