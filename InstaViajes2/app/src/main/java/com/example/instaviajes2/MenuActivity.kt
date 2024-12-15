package com.example.instaviajes2

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MenuActivity : AppCompatActivity(),
    ScaleGestureDetector.OnScaleGestureListener,
    GestureDetector.OnGestureListener {

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var gestureDetector: GestureDetector
    private var scaleFactor = 1f

    private val SPEECH_REQUEST_CODE = 1
    private val MIN_DISTANCE = 200
    private val MIN_VELOCITY = 200
    private val MIN_SCALE = 0.1f
    private val MAX_SCALE = 5.0f

    private lateinit var layouts: List<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        scaleGestureDetector = ScaleGestureDetector(this, this)
        gestureDetector = GestureDetector(this, this)

        val layoutAvailableTrip = findViewById<LinearLayout>(R.id.layoutAvailableTrips)
        val layoutLogout = findViewById<LinearLayout>(R.id.layoutLogout)
        val layoutProfile = findViewById<LinearLayout>(R.id.layoutProfile)
        val layoutMyTrips = findViewById<LinearLayout>(R.id.layoutMyTrips)
        val layoutCreateTrip = findViewById<LinearLayout>(R.id.layoutCreateTrip)
        val layoutVoiceControl = findViewById<LinearLayout>(R.id.layoutVoiceControl)
        val layoutSettings = findViewById<LinearLayout>(R.id.layoutSettings)
        val layoutSupport = findViewById<LinearLayout>(R.id.layoutSupport)

        layouts = listOf(
            layoutAvailableTrip,
            layoutLogout,
            layoutProfile,
            layoutMyTrips,
            layoutCreateTrip,
            layoutVoiceControl,
            layoutSettings,
            layoutSupport
        )

        layoutLogout.setOnClickListener {
            val pantallaLogin = Intent(this, LoginActivity::class.java)
            startActivity(pantallaLogin)
        }

        layoutAvailableTrip.setOnClickListener {
            val pantallaViajes = Intent(this, TripsActivity::class.java)
            startActivity(pantallaViajes)
        }

        layoutProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        layoutVoiceControl.setOnClickListener {
            startSpeechToText()
        }

        layoutMyTrips.setOnClickListener {
            val intent = Intent(this, MyTripsActivity::class.java)
            startActivity(intent)
        }

        layoutCreateTrip.setOnClickListener {
            val intent = Intent(this, NewTripActivity::class.java)
            startActivity(intent)
        }

        layoutSettings.setOnClickListener {
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
        }

        layoutSupport.setOnClickListener {
            Toast.makeText(this, "Soporte aún no implementado", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            result?.let {
                val command = it[0].lowercase(Locale.getDefault())

                when (command) {
                    "perfil" -> startActivity(Intent(this, ProfileActivity::class.java))
                    "viajes disponibles" -> startActivity(Intent(this, TripsActivity::class.java))
                    "mis viajes" -> startActivity(Intent(this, MyTripsActivity::class.java))
                    "crear viaje" -> startActivity(Intent(this, NewTripActivity::class.java))
                    "configuración" -> startActivity(Intent(this, ConfigurationActivity::class.java))
                    "salir", "cerrar sesión" -> startActivity(Intent(this, LoginActivity::class.java))
                    else -> Toast.makeText(this, "Comando no reconocido: $command", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val isScaleGesture = scaleGestureDetector.onTouchEvent(event)
        val isGesture = gestureDetector.onTouchEvent(event)
        return isScaleGesture || isGesture || super.onTouchEvent(event)
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (e1 != null && e2 != null) {
            val deltaX = e2.x - e1.x
            if (Math.abs(deltaX) > MIN_DISTANCE && Math.abs(velocityX) > MIN_VELOCITY) {
                if (deltaX > 0) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                }
            }
        }
        return true
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        scaleFactor *= detector.scaleFactor
        scaleFactor = scaleFactor.coerceIn(MIN_SCALE, MAX_SCALE)

        for (layout in layouts) {
            layout.scaleX = scaleFactor
            layout.scaleY = scaleFactor
        }

        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean = true

    override fun onScaleEnd(detector: ScaleGestureDetector) {}

    override fun onDown(e: MotionEvent): Boolean = false

    override fun onShowPress(e: MotionEvent) {}

    override fun onSingleTapUp(e: MotionEvent): Boolean = false

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean = false

    override fun onLongPress(e: MotionEvent) {}
}
