package com.example.encriptador

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private val RESULT_CODE = 1
    private lateinit var textOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textOutput = findViewById(R.id.textInput)
        val boton = findViewById<Button>(R.id.buttonEncriptar)
        val textInput = findViewById<TextInputEditText>(R.id.textInput)
        val spinner = findViewById<Spinner>(R.id.spinner)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.encryption_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val bundle = intent.extras
        val datoRecibido = bundle?.getString("data")
        if (!datoRecibido.isNullOrEmpty()) {
            textInput.setText(datoRecibido)
        }

        boton.setOnClickListener {
            val texto = textInput.text.toString()
            val selectedAlgorithm = spinner.selectedItem.toString()
            val result: String? = when (selectedAlgorithm) {
                "Codigo Cesar" -> encriptadoCesar(texto, 7)
                "Vigenere" -> encriptadorVigenere(texto, "CLAVE")
                "Transposicion" -> cifrarTransposicion(texto, 5)
                else -> "Error"
            }

            val resultScreen = Intent(this, ResultActivity::class.java)
            resultScreen.putExtra("data", result)
            resultScreen.putExtra("algorithm", selectedAlgorithm)
            startActivityForResult(resultScreen, RESULT_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_CODE && resultCode == RESULT_OK) {
            val datoRecibido = data?.getStringExtra("data")
            textOutput.text = datoRecibido
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

    fun cifrarTransposicion(texto: String, clave: Int): String {
        val columnas = clave
        val filas = (texto.length + columnas - 1) / columnas
        val matriz = Array(filas) { CharArray(columnas) { ' ' } }

        var index = 0
        for (i in 0 until filas) {
            for (j in 0 until columnas) {
                if (index < texto.length) {
                    matriz[i][j] = texto[index++]
                }
            }
        }

        val resultado = StringBuilder()
        for (j in 0 until columnas) {
            for (i in 0 until filas) {
                if (matriz[i][j] != ' ') {
                    resultado.append(matriz[i][j])
                }
            }
        }

        return resultado.toString()
    }
}
