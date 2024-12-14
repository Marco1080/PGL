package com.example.instaviajes2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import kotlin.random.Random

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

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
        noRegistrado.setOnClickListener {
            val pantallaRegistro = Intent(this, RegisterActivity::class.java)
            startActivity(pantallaRegistro)
        }

        val buttonMenu = findViewById<Button>(R.id.buttonMenu)
        buttonMenu.setOnClickListener {
            val pantallaMenu = Intent(this, MenuActivity::class.java)
            startActivity(pantallaMenu)
        }

    }
}
