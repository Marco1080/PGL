package com.example.recuperacionpgl_marco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val btnAlta = findViewById<Button>(R.id.btnAlta)
        btnAlta.setOnClickListener{
            val pantallaAlta = Intent(this, AltaCuentaActivity::class.java)
            startActivity(pantallaAlta)
        }

        val btnConsulta = findViewById<Button>(R.id.btnConsulta)
        btnConsulta.setOnClickListener{
            val pantallaConsulta = Intent(this, ConsultaCuentaActivity::class.java)
            startActivity(pantallaConsulta)
        }

        val btnExtraer = findViewById<Button>(R.id.btnExtraer)
        btnExtraer.setOnClickListener{
            val pantallaExtraer = Intent(this, ExtraerCuentaActivity::class.java)
            startActivity(pantallaExtraer)
        }

        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        btnIngresar.setOnClickListener{
            val pantallaIngresar = Intent(this, IngresarCuentaActivity::class.java)
            startActivity(pantallaIngresar)
        }
    }
}
