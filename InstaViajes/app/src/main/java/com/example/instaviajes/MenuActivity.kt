package com.example.instaviajes

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        val userId = intent.getIntExtra("USER_ID", -1)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val goToLogin = findViewById<TextView>(R.id.btnCerrarSesion)
        goToLogin.setOnClickListener {
            val pantallaLogin = Intent(this, LoginActivity::class.java)
            startActivity(pantallaLogin)
        }

        val goToprofile = findViewById<TextView>(R.id.btnPerfil)
        goToprofile.setOnClickListener {
            val pantallaPerfil = Intent(this, ProfileActivity::class.java)
            pantallaPerfil.putExtra("USER_ID", userId)
            startActivity(pantallaPerfil)
        }

        val goToNewTrip = findViewById<TextView>(R.id.btnCrearViaje)
        goToNewTrip.setOnClickListener {
            val pantallaCrearViaje = Intent(this, NuevoViajeActivity::class.java)
            pantallaCrearViaje.putExtra("USER_ID", userId)
            startActivity(pantallaCrearViaje)
        }

        val goToTripList = findViewById<TextView>(R.id.btnVerViajes)
        goToTripList.setOnClickListener {
            val pantallaListaViajes = Intent(this, ListaViajesActivity::class.java)
            pantallaListaViajes.putExtra("USER_ID", userId)
            startActivity(pantallaListaViajes)
        }
    }
}
