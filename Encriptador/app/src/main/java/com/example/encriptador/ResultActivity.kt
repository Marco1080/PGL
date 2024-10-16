package com.example.encriptador

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
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
        val dato = bundle?.getString("data")
        val textInput = findViewById<TextView>(R.id.textView)
        textInput.setText(dato.toString())

        val buttonDescifrar = findViewById<Button>(R.id.buttonDescifrar)
        buttonDescifrar.setOnClickListener{
            val cifradorPantalla = Intent(this, MainActivity::class.java)
            startActivity(cifradorPantalla)
        }

    }
}