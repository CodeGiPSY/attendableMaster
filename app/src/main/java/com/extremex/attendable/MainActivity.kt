package com.extremex.attendable

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val loginActivityScreen = Intent(this, LoginActivity::class.java)
        val adminActivityScreen = Intent(this, AdminActivity::class.java)

        val pref = getSharedPreferences("creds-for-auth", MODE_PRIVATE)
        val validate = getSharedPreferences("validation", MODE_PRIVATE)
        val savedUname = pref.getString("EMAIL_ID", "")
        val savedPass = pref.getString("PASSWORD", "")
        val usrname = validate.getString("USERNAME", "")
        val passwd = validate.getString("PASSWORD", "")

        if (usrname == savedUname){
            if (passwd == savedPass){
                startActivity(adminActivityScreen)
            } else {
                Toast.makeText(this, "Incorrect Password",Toast.LENGTH_SHORT).show()
                startActivity(loginActivityScreen)
            }
        } else {
            Toast.makeText(this, "Incorrect Username",Toast.LENGTH_SHORT).show()
            startActivity(loginActivityScreen)
        }
    }
}