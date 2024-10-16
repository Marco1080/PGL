package com.example.encriptador

import android.content.Intent
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
import javax.xml.transform.Result

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
            //var result = encriptadoCesar(texto,7)
            var result = cifrarAfinado(texto,7, 7)
            textOutput.setText(result)

            val resultScreen = Intent(this, ResultActivity::class.java)
            resultScreen.putExtra("data", result)
            startActivity(resultScreen)
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
    fun cifrarAfinado(texto: String, a: Int, b: Int): String {
        val n = 26
        val resultado = StringBuilder()

        for (caracter in texto) {
            if (caracter.isLetter()) {
                val x = if (caracter.isUpperCase()) {
                    caracter - 'A'
                } else {
                    caracter - 'a'
                }

                val cifrado = (a * x + b) % n

                val letraCifrada = if (caracter.isUpperCase()) {
                    'A' + cifrado
                } else {
                    'a' + cifrado
                }

                resultado.append(letraCifrada)
            } else {
                resultado.append(caracter)
            }
        }

        return resultado.toString()
    }
    fun encriptadorVigenere(texto: String, clave: String): String {
        val resultado = StringBuilder()
        val n = 26
        val claveNormalizada = clave.uppercase().filter { it.isLetter() }
        var indiceClave = 0

        for (caracter in texto) {
            if (caracter.isLetter()) {
                val desplazamiento = claveNormalizada[indiceClave % claveNormalizada.length] - 'A'
                val x = if (caracter.isUpperCase()) {
                    caracter - 'A'
                } else {
                    caracter - 'a'
                }

                val nuevoValor = (x + desplazamiento) % n
                val letraCifrada = if (caracter.isUpperCase()) {
                    'A' + nuevoValor
                } else {
                    'a' + nuevoValor
                }

                resultado.append(letraCifrada)
                indiceClave++
            } else {
                resultado.append(caracter)
            }
        }

        return resultado.toString()
    }

}