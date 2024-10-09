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

        val listadoRespuestas = arrayOf(
            "Pikachu",
            "Charmander",
            "Bulbasaur",
            "Squirtle",
            "Jigglypuff",
            "Gengar",
            "Mew",
            "Eevee",
            "Charizard",
            "Mew"
        )

        val button = findViewById<Button>(R.id.buttonEnviar)
        var contador = 0

        button.setOnClickListener {
            contador = 0

            for (i in 1..10) {
                val radioGroup = findViewById<RadioGroup>(resources.getIdentifier("radioGroup$i", "id", packageName))
                val selectedRadioButtonId = radioGroup.checkedRadioButtonId
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)

                val respuestaTextView = findViewById<TextView>(resources.getIdentifier("textViewRespuesta$i", "id", packageName))
                respuestaTextView.visibility = TextView.VISIBLE

                if (selectedRadioButtonId != -1) {
                    val selectedAnswer = selectedRadioButton.text.toString()
                    if (selectedAnswer == listadoRespuestas[i - 1]) {
                        contador++
                    } else {
                        respuestaTextView.text = "Respuesta correcta: ${listadoRespuestas[i - 1]}"
                    }
                } else {
                    respuestaTextView.text = "No seleccionaste respuesta."
                }
            }

            val resultadoTextView = findViewById<TextView>(R.id.textViewResultado)
            resultadoTextView.text = "Respuestas correctas: $contador"
            resultadoTextView.visibility = TextView.VISIBLE
            Log.d("TAG", "Respuestas correctas: $contador")
        }
    }
}
