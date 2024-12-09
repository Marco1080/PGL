package com.example.instaviajes

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class NuevoViajeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_viaje)

        val textViewFechaInicio: TextView = findViewById(R.id.textViewFechaInicio)
        val textViewFechaFin: TextView = findViewById(R.id.textViewFechaFin)
        val editTextTitulo: EditText = findViewById(R.id.editTextTitulo)
        val editTextDescripcion: EditText = findViewById(R.id.editTextDescripcion)
        val imageViewPortada: ImageView = findViewById(R.id.imageViewPortada)
        val btnSubirImagen: Button = findViewById(R.id.btnSubirImagen)
        val btnCrearViaje: Button = findViewById(R.id.btnCrearViaje)

        val userId = intent.getIntExtra("USER_ID", -1)

        if (userId == -1) {
            Toast.makeText(
                this,
                "Error: No se ha podido obtener el ID del usuario.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        textViewFechaInicio.setOnClickListener {
            showDatePicker { selectedDate ->
                textViewFechaInicio.text = selectedDate
            }
        }

        textViewFechaFin.setOnClickListener {
            showDatePicker { selectedDate ->
                textViewFechaFin.text = selectedDate
            }
        }

        btnCrearViaje.setOnClickListener {
            val titulo = editTextTitulo.text.toString()
            val descripcion = editTextDescripcion.text.toString()
            val fechaInicio = textViewFechaInicio.text.toString()
            val fechaFin = textViewFechaFin.text.toString()
            val imagenPortada = ""

            if (titulo.isNotEmpty() && fechaInicio.isNotEmpty() && fechaFin.isNotEmpty()) {
                insertarViaje(titulo, descripcion, fechaInicio, fechaFin, userId, imagenPortada)
            } else {
                Toast.makeText(
                    this,
                    "Por favor, completa todos los campos obligatorios",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val date = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
            onDateSelected(date)
        }, year, month, day).show()
    }

    private fun insertarViaje(
        titulo: String,
        descripcion: String?,
        fechaInicio: String,
        fechaFin: String,
        creadorId: Int,
        imagenPortada: String?
    ) {
        val dbHelper = DataBaseHelper(this)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("titulo", titulo)
            put("descripcion", descripcion)
            put("fechaInicio", fechaInicio)
            put("fechaFin", fechaFin)
            put("creadorId", creadorId)
            put("imagenPortada", imagenPortada ?: "")
        }

        val id = db.insert("viajes", null, values)

        if (id == -1L) {
            Toast.makeText(this, "Error al crear el viaje", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Viaje creado con éxito", Toast.LENGTH_SHORT).show()
            Log.d("Viajes", "Nuevo viaje insertado con id: $id")
        }

        db.close()
    }
}
