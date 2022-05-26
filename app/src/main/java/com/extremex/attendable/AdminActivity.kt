package com.extremex.attendable

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class AdminActivity : AppCompatActivity() {

    private lateinit var fireBaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val signOutButton = findViewById<Button>(R.id.signoutBtn)
        val loginScreen =  Intent(this,LoginActivity::class.java)

        fireBaseAuth = FirebaseAuth.getInstance()

        signOutButton.setOnClickListener {
            fireBaseAuth.signOut()
            startActivity(loginScreen);
            finish()
        }
    }
}