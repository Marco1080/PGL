package com.example.instaviajes2

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.ScaleGestureDetector
import java.util.Locale

class MenuActivity : AppCompatActivity(), ScaleGestureDetector.OnScaleGestureListener {

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1f
    private val SPEECH_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        // Inicializar ScaleGestureDetector
        scaleGestureDetector = ScaleGestureDetector(this, this)

        // Encuentra todos los LinearLayouts a los que deseas aplicar el zoom
        val layoutLogout = findViewById<LinearLayout>(R.id.layoutLogout)
        val layoutProfile = findViewById<LinearLayout>(R.id.layoutProfile)
        val layoutMyTrips = findViewById<LinearLayout>(R.id.layoutMyTrips)
        val layoutCreateTrip = findViewById<LinearLayout>(R.id.layoutCreateTrip)
        val layoutVoiceControl = findViewById<LinearLayout>(R.id.layoutVoiceControl)
        val layoutSettings = findViewById<LinearLayout>(R.id.layoutSettings)
        val layoutSupport = findViewById<LinearLayout>(R.id.layoutSupport)

        // Lista de layouts para facilitar la iteración
        val layouts = listOf(
            layoutLogout, layoutProfile, layoutMyTrips, layoutCreateTrip,
            layoutVoiceControl, layoutSettings, layoutSupport
        )

        // Aplicar un OnClickListener a cada layout
        layoutLogout.setOnClickListener {
            val pantallaLogin = Intent(this, LoginActivity::class.java)
            startActivity(pantallaLogin)
        }

        layoutProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Configurar reconocimiento de voz en layoutVoiceControl
        layoutVoiceControl.setOnClickListener {
            startSpeechToText()
        }

        // Para el manejo del "Edge to Edge" y el padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Método para iniciar el reconocimiento de voz
     */
    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga un comando para navegar entre las opciones")
        }

        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(this, "El reconocimiento de voz no está disponible", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Método que recibe el resultado del reconocimiento de voz
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            result?.let {
                val command = it[0].lowercase(Locale.getDefault())

                when (command) {
                    "perfil" -> {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                    }
                    "mis viajes" -> {
                        val intent = Intent(this, MyTripsActivity::class.java)
                        startActivity(intent)
                    }
                    "crear viaje" -> {
                        val intent = Intent(this, NewTripActivity::class.java)
                        startActivity(intent)
                    }
                    "configuración" -> {
                        val intent = Intent(this, ConfigurationActivity::class.java)
                        startActivity(intent)
                    }
                    "salir", "cerrar sesión" -> {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else -> {
                        Toast.makeText(this, "Comando no reconocido: $command", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // Método de gestos de zoom
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    // Gestores de los eventos de zoom
    override fun onScale(detector: ScaleGestureDetector): Boolean {
        scaleFactor *= detector.scaleFactor
        scaleFactor = scaleFactor.coerceIn(0.1f, 5.0f)  // Limita el zoom entre 0.1x y 5x

        // Aplica el zoom a cada LinearLayout
        val layouts = listOf(
            findViewById<LinearLayout>(R.id.layoutLogout),
            findViewById<LinearLayout>(R.id.layoutProfile),
            findViewById<LinearLayout>(R.id.layoutMyTrips),
            findViewById<LinearLayout>(R.id.layoutCreateTrip),
            findViewById<LinearLayout>(R.id.layoutVoiceControl),
            findViewById<LinearLayout>(R.id.layoutSettings),
            findViewById<LinearLayout>(R.id.layoutSupport)
        )

        for (layout in layouts) {
            layout.scaleX = scaleFactor
            layout.scaleY = scaleFactor
        }
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector) {
        // Puedes hacer algo cuando termine el zoom si es necesario
    }
}
