package com.extremex.attendable

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button as Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.loginBtn)
        val signUp = findViewById<Button>(R.id.signinBtn)

        val AdminActivityScreen = Intent(this, AdminActivity::class.java)
        val SignUpActivityScreen = Intent(this, SignUpActivity::class.java)

        val userName: EditText = this@LoginActivity.findViewById(R.id.username)
        val userPassWord: EditText = this@LoginActivity.findViewById(R.id.password)

        val pref = getSharedPreferences("creds-for-auth", MODE_PRIVATE)
        val savedUname = pref.getString("EMAIL_ID", "")
        val savedPass = pref.getString("PASSWORD", "")


        loginButton.setOnClickListener {
            if (userName.text.toString() == savedUname && !userName.text.toString().isNullOrBlank()) {
                if (userPassWord.text.toString() == savedPass && !userPassWord.text.toString().isNullOrBlank()){
                    val validate = getSharedPreferences("validation", MODE_PRIVATE)
                    val editor = validate.edit()

                    editor.putString("USERNAME", userName.text.toString())
                    editor.putString("PASSWORD", userPassWord.text.toString())
                    editor.apply()

                    startActivity(AdminActivityScreen)

                } else {
                    Toast.makeText(this@LoginActivity, "Incorrect Password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@LoginActivity, "Incorrect Username", Toast.LENGTH_SHORT).show()
            }
        }
        signUp.setOnClickListener { startActivity(SignUpActivityScreen) }
    }
}