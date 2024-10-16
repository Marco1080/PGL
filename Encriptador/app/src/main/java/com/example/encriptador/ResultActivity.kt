package com.example.encriptador

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle = intent.extras
        val dato = bundle?.getString("data") ?: ""
        val algoritmo = bundle?.getString("algorithm") ?: "Error"
        val textInput = findViewById<TextView>(R.id.textViewOutput)
        textInput.text = dato

        val buttonDescifrar = findViewById<Button>(R.id.buttonDescifrar)
        buttonDescifrar.setOnClickListener {
            val resultadoDescifrado: String = when (algoritmo) {
                "Codigo Cesar" -> descifrarCesar(dato, 7)
                "Vigenere" -> descifrarVigenere(dato, "CLAVE")
                "Transposicion" -> descifrarTransposicion(dato, 5)
                else -> "Error"
            }

            val intent = Intent()
            intent.putExtra("data", resultadoDescifrado)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun descifrarCesar(clave: String, desplazamiento: Int): String {
        return encriptadoCesar(clave, 26 - desplazamiento)
    }

    private fun descifrarVigenere(texto: String, clave: String): String {
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

                val nuevoValor = (x - desplazamiento + n) % n
                val letraDescifrada = if (caracter.isUpperCase()) {
                    'A' + nuevoValor
                } else {
                    'a' + nuevoValor
                }

                resultado.append(letraDescifrada)
                indiceClave++
            } else {
                resultado.append(caracter)
            }
        }

        return resultado.toString()
    }

    private fun encriptadoCesar(clave: String, desplazamiento: Int): String {
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

    private fun descifrarTransposicion(texto: String, clave: Int): String {
        val columnas = clave
        val filas = (texto.length + columnas - 1) / columnas
        val matriz = Array(filas) { CharArray(columnas) { ' ' } }

        var index = 0
        for (j in 0 until columnas) {
            for (i in 0 until filas) {
                if (index < texto.length) {
                    matriz[i][j] = texto[index++]
                }
            }
        }

        val resultado = StringBuilder()
        for (i in 0 until filas) {
            for (j in 0 until columnas) {
                if (matriz[i][j] != ' ') {
                    resultado.append(matriz[i][j])
                }
            }
        }
        return resultado.toString()
    }
}
