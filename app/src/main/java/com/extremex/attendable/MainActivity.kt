package com.extremex.attendable

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        val loginActivityScreen = Intent(this, LoginActivity::class.java)
        startActivity(loginActivityScreen)
    }
}