package com.example.instaviajes2

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class NewTripActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_trip)

        val spinner = findViewById<Spinner>(R.id.ubicacion)
        val items = arrayOf(
            "Selecciona ubicación",
            "Andalucía", "Aragón", "Asturias", "Canarias", "Cantabria",
            "Castilla y León", "Castilla-La Mancha", "Cataluña", "Extremadura",
            "Galicia", "Madrid", "Murcia", "Navarra", "La Rioja",
            "País Vasco", "Comunidad Valenciana", "Islas Baleares"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val editTextTitle = findViewById<EditText>(R.id.editTextText)
        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)
        val editTextDate = findViewById<EditText>(R.id.editTextDate)
        val buttonSubmitTrip = findViewById<Button>(R.id.buttonSubmitTrip)

        val username = intent.getStringExtra("username") ?: "Usuario desconocido"

        // Mostrar selector de fecha cuando el usuario hace clic en el campo de fecha
        editTextDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editTextDate.setText(date)
            }, year, month, day)

            datePickerDialog.show()
        }

        buttonSubmitTrip.setOnClickListener {
            val title = editTextTitle.text.toString().trim()
            val description = editTextDescription.text.toString().trim()
            val date = editTextDate.text.toString().trim()
            val location = spinner.selectedItem.toString()

            // Validación de campos
            if (title.isEmpty() || description.isEmpty() || date.isEmpty() || location == "Selecciona ubicación") {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar el viaje en la base de datos
            val dbHelper = DataBaseHelper(this)
            val success = dbHelper.insertTrip(title, description, date, location, username)

            if (success) {
                Toast.makeText(this, "Viaje creado con éxito", Toast.LENGTH_SHORT).show()

                // Redirigir al usuario al menú
                val intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
                finish() // Cierra la actividad para que no pueda volver atrás
            } else {
                Toast.makeText(this, "Error al guardar el viaje", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
