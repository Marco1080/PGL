package com.example.instaviajes2

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gestureDetector = GestureDetector(this, this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (e1 != null && e2 != null) {
            val deltaX = e2.x - e1.x
            if (Math.abs(deltaX) > 200 && Math.abs(velocityX) > 200) {
                if (deltaX > 0) {
                    navigateToPreviousActivity()
                } else {
                    navigateToNextActivity()
                }
            }
        }
        return true
    }

    abstract fun navigateToPreviousActivity()
    abstract fun navigateToNextActivity()
}
