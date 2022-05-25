package com.extremex.attendable

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.extremex.kotex_libs.WebHooks
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    // firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

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

        var courseCodes = resources.getStringArray(R.array.course_code)
        val roleName = arrayListOf("teacher","student","admin")

        var rolePointer = 2
        var coursePointer = 1

        course.text = courseCodes[coursePointer]

        privRole.setOnClickListener {
            rolePointer -= 1
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
            if (rolePointer == roleName.size) {
                rolePointer = roleName.size -1
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

            if (email.text.toString().isBlank() && password.text.toString().isBlank()) {
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
            }
            if (password.text.toString() == verifyPassword.text.toString()){
                VerifyAuth(email.text.toString(), password.text.toString())
            } else {
                Toast.makeText(this, "Password does not match!", Toast.LENGTH_SHORT).show()
            }

        }

        backButton.setOnClickListener {
            finish()
            startActivity(LoginActivityScreen)
        }
    }
    private fun VerifyAuth(email: String, password: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Invalid Email Address",Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Password is Incorrect",Toast.LENGTH_SHORT).show()
        } else {
            fireBaseLogin(email, password)
        }


    }

    private fun fireBaseLogin(email: String, password: String) {
        firebaseAuth = FirebaseAuth.getInstance()
        val showProgress: ProgressDialog = ProgressDialog(this)
        showProgress.setTitle("Please wait")
        showProgress.setMessage("Signing in...")
        showProgress.setCancelable(false)
        showProgress.show()
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                showProgress.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val userEmail = firebaseUser!!.email
                Toast.makeText(this,"Signed in Successfully as $userEmail",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            .addOnFailureListener {
                showProgress.dismiss()
                Toast.makeText(this,"Failed to Sign in.",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}