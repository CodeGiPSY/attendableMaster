package com.extremex.attendable

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.extremex.kotex_libs.WebHooks

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Creating Intent To move Through Different Activities
        val LoginActivityScreen :Intent = Intent(this, LoginActivity::class.java)

        // View elements Binding
        val backButton = findViewById<ImageView>(R.id.BackBtn)
        val firstName :EditText = this.findViewById(R.id.FirstName)
        val lastName :EditText = this.findViewById(R.id.SirName)
        val email :EditText = this.findViewById(R.id.EmailAddress)
        val password :EditText = this.findViewById(R.id.NewPassword)
        val verifyPassword :EditText = this.findViewById(R.id.ConfirmPassword)
        val privRole :ImageView = this.findViewById(R.id.PrivRole)
        val role :TextView = this.findViewById(R.id.Role)
        val nextRole :ImageView = this.findViewById(R.id.NextRole)
        val privCourse :ImageView = this.findViewById(R.id.PrivCourse)
        val course :TextView = this.findViewById(R.id.Course)
        val nextCourse :ImageView = this.findViewById(R.id.NextCourse)
        val numberID :EditText = this.findViewById(R.id.IDNumber)
        val signUpButton :Button = this.findViewById(R.id.SignUpButton)

        // storage access for shared storage in /android/data/com.extremex.attendable/SharedPreferences/
        //
        val pref = getSharedPreferences("creds-for-auth", MODE_PRIVATE)
        val editor = pref.edit()
        //
        //

        var courseCodes = resources.getStringArray(R.array.course_code)
        val roleName = arrayListOf<String>("teacher","student","admin")

        var rolePointer = 2
        var coursePointer = 1

        course.text = courseCodes[coursePointer]

        privRole.setOnClickListener {
            rolePointer -= 14
            if (rolePointer == 0) {
                rolePointer = 0
                privRole.alpha = 0.3f
                privRole.isClickable = false
                rolePointer = 0
                role.text = roleName[rolePointer]
                nextRole.isClickable = true
                nextRole.alpha = 1.0f

            } else {
                privRole.alpha = 1.0f
                privRole.isClickable = true
                role.text = roleName[rolePointer]
            }

        }

        nextRole.setOnClickListener {
            rolePointer += 1
            if (rolePointer == roleName.size-1) {
                nextRole.alpha = 0.3f
                nextRole.isClickable = false
                role.text = roleName[rolePointer]
                privRole.alpha = 1.0f
                privRole.isClickable = true

            } else {
                nextRole.alpha = 1.0f
                nextRole.isClickable = true
                role.text = roleName[rolePointer]
            }

        }

        privCourse.setOnClickListener {
            coursePointer -= 1
            if(coursePointer == 0){
                it.alpha = 0.3f
                it.isClickable = false
                course.text = courseCodes[coursePointer]
                nextCourse.alpha = 1.0f
                nextCourse.isClickable = true

            } else {
                it.alpha = 1.0f
                it.isClickable = true
                course.text = courseCodes[coursePointer]
            }
        }

        nextCourse.setOnClickListener {
            coursePointer += 1
            if (coursePointer == courseCodes.size -1) {
                coursePointer = courseCodes.size -1
                it.alpha = 0.3f
                it.isClickable = false
                course.text = courseCodes[coursePointer]
                privCourse.alpha = 1.0f
                privCourse.isClickable = true

            } else {
                it.alpha = 1.0f
                it.isClickable = true
                course.text = courseCodes[coursePointer]
            }

        }

        signUpButton.setOnClickListener{
            if (!firstName.text.isNullOrBlank() && !lastName.text.isNullOrBlank() && !email.text.isNullOrBlank() &&
                !password.text.isNullOrBlank() && !verifyPassword.text.isNullOrBlank() && !numberID.text.isNullOrBlank()){

                if (verifyPassword.text.toString() == password.text.toString()){

                    editor.putString("FIRST_NAME", firstName.text.toString())
                    editor.putString("LAST_NAME",lastName.text.toString())
                    editor.putString("EMAIL_ID", email.text.toString())
                    editor.putString("PASSWORD", password.text.toString())
                    editor.putString("ACCT_TYPE", role.text.toString())
                    editor.putString("COURSE_CODE", course.text.toString())
                    editor.putString("ID_NUMBER", numberID.text.toString())
                    editor.apply()

                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()

                    onBackPressed()

                } else {
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fill in the empty Fields", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            finish()
            startActivity(LoginActivityScreen)
        }
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}