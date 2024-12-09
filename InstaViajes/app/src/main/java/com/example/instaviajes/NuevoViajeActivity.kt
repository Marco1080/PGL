package com.example.instaviajes

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class NuevoViajeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_viaje)

        val textViewFechaInicio: TextView = findViewById(R.id.textViewFechaInicio)
        val textViewFechaFin: TextView = findViewById(R.id.textViewFechaFin)

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

        val goToMenu = findViewById<TextView>(R.id.btnCrearViaje)
        goToMenu.setOnClickListener {
            val pantallaMenu = Intent(this, MenuActivity::class.java)
            startActivity(pantallaMenu)
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


}
