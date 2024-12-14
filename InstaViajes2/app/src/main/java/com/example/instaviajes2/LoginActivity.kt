package com.example.instaviajes2

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

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


        val imageView: ImageView = findViewById(R.id.backgroundImage)

        val images = arrayOf(
            R.drawable.montania1,
            R.drawable.montania2,
            R.drawable.playa1,
            R.drawable.playa2
        )

        val randomImage = images[Random.nextInt(images.size)]

        imageView.setImageResource(randomImage)

        val noRegistrado = findViewById<TextView>(R.id.noRegistrado)
        noRegistrado.setOnClickListener{
            val pantallaRegistro = Intent(this, RegisterActivity::class.java)
            startActivity(pantallaRegistro)
        }
    }
}
