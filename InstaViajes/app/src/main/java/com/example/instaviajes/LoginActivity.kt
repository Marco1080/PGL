package com.example.instaviajes

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val goToRegister = findViewById<TextView>(R.id.goToRegister)
        goToRegister.setOnClickListener{
            val pantallaRegistro = Intent(this, RegisterActivity::class.java)
            startActivity(pantallaRegistro)
        }

        val goToMenu = findViewById<TextView>(R.id.btnLogin)
        goToMenu.setOnClickListener{
            val pantallaMenu = Intent(this, MenuActivity::class.java)
            startActivity(pantallaMenu)
        }
    }
}