package com.example.practica_5_cuestionario_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PerfilUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val user = intent.getStringExtra("user")
        val nota = intent.getIntExtra("nota", -1)

        user?.let {
            findViewById<TextView>(R.id.textViewUser).text = it
            if (nota >= 0) {
                findViewById<TextView>(R.id.textViewNota).text = "Nota: $nota"
            } else {
                findViewById<TextView>(R.id.textViewNota).text = "Nota no disponible"
            }
        } ?: run {
            findViewById<TextView>(R.id.textViewUser).text = "Usuario no disponible"
            findViewById<TextView>(R.id.textViewNota).text = "Nota no disponible"
        }

        findViewById<Button>(R.id.buttonCuestionario).setOnClickListener {
            val cuestionario = Intent(this, Pregunta1::class.java)
            cuestionario.putExtra("user", user)
            startActivity(cuestionario)
        }
    }
}
