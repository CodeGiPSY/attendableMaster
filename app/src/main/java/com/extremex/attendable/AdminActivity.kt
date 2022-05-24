package com.extremex.attendable

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val signOutButton = findViewById<Button>(R.id.signoutBtn)
        val loginScreen =  Intent(this,LoginActivity::class.java)

        val validate = getSharedPreferences("validation", MODE_PRIVATE)
        val save = validate.edit()


        signOutButton.setOnClickListener {
            save.remove("USERNAME")
            save.remove("PASSWORD")
            save.apply()

            startActivity(loginScreen);
            finish()
        }
    }
}