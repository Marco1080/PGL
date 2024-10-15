package com.example.encriptador

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.nio.file.Files

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var textOutput = findViewById<TextView>(R.id.textViewOutput)
        var boton = findViewById<Button>(R.id.buttonEncriptar)
        var textInput = findViewById<TextInputEditText>(R.id.textInput)
        boton.setOnClickListener{
            var texto = textInput.text.toString()
            var result = encriptadoCesar(texto,7)
            textOutput.setText(result)
        }

    }
    fun encriptadoCesar(clave: String, desplazamiento: Int): String {
        val claveEncriptada = StringBuilder()

        for (caracter in clave) {
            if (caracter.isLetter()) {
                val base = if (caracter.isUpperCase()) 'A' else 'a'
                val nuevaLetra = ((caracter - base + desplazamiento) % 26 + base.toInt()).toChar()
                claveEncriptada.append(nuevaLetra)
            } else {
                claveEncriptada.append(caracter)
            }
        }

        return claveEncriptada.toString()
    }
}