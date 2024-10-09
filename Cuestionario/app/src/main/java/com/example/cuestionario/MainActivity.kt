package com.example.cuestionario

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listadoRespuestas = arrayOf("A", "B", "C", "A", "B", "C", "A", "B", "C")
        print("Hello, world!")
        Log.d("TAG", "Hello, world!");
        val button = findViewById<Button>(R.id.button)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup1)
        val respuestaTextView = findViewById<TextView>(R.id.textViewRespuesta1)

        button.setOnClickListener {
           val selectedRadioButtonId = radioGroup.checkedRadioButtonId

            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)


                respuestaTextView.text = when (selectedRadioButtonId) {
                    R.id.radioButtonRespuesta1A -> listadoRespuestas[0]
                    else -> "La respuesta correcta era " + listadoRespuestas[0]
                }
            var contador = 1;
            while (contador < 10) {
                var direccion = "textViewPregunta$contador"
                val selectedRadioButton = findViewById<RadioButton>(direccion)
                contador++

            }


        }
    }
}
