package com.example.instaviajes2

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class NewTripActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_trip)


        val spinner = findViewById<Spinner>(R.id.ubicacion)
        val items = arrayOf(
            "Ubicacion",
            "Andalucía",
            "Aragón",
            "Asturias",
            "Canarias",
            "Cantabria",
            "Castilla y León",
            "Castilla-La Mancha",
            "Cataluña",
            "Extremadura",
            "Galicia",
            "Madrid",
            "Murcia",
            "Navarra",
            "La Rioja",
            "País Vasco",
            "Comunidad Valenciana",
            "Islas Baleares"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        val editTextDate = findViewById<EditText>(R.id.editTextDate)

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
    }
}
