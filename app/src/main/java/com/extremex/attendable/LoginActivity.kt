package com.extremex.attendable

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Button as Button

class LoginActivity : AppCompatActivity() {

    // fireBase Auth
    private lateinit var fireBaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    //
    private val roleName = arrayListOf("teacher","Student","Admin050")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.loginBtn)
        val signUp = findViewById<Button>(R.id.signinBtn)

        val SignUpActivityScreen = Intent(this, SignUpActivity::class.java)

        val userName: EditText = this@LoginActivity.findViewById(R.id.username)
        val userPassWord: EditText = this@LoginActivity.findViewById(R.id.password)


        loginButton.setOnClickListener {
            // validation

            if (userName.text.toString().isBlank() && userPassWord.text.toString().isBlank()) {
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
            } else {
                VerifyAuth(userName.text.toString(), userPassWord.text.toString())
            }
        }
        signUp.setOnClickListener { startActivity(SignUpActivityScreen) }
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
        val progress: ProgressDialog = ProgressDialog(this)
        progress.setTitle("Please Wait")
        progress.setMessage("Logging in...")
        progress.setCancelable(false)
        progress.show()
        fireBaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progress.dismiss()
                val firebaseUser = fireBaseAuth.currentUser
                firebaseDatabase = FirebaseDatabase.getInstance("https://signup-attendable-default-rtdb.europe-west1.firebasedatabase.app/")
                databaseReference = firebaseDatabase.getReferenceFromUrl("https://signup-attendable-default-rtdb.europe-west1.firebasedatabase.app/")

                val ud = firebaseUser?.email?.split("@")
                val uid = ud?.get(0)
                databaseReference.child("users").child(uid.toString()).get().addOnSuccessListener {
                    if (it.exists()){
                        val key = it.child("role").value

                        Toast.makeText(this,"Logged in as ${it.child("firstname").value} ${it.child("lastname").value}",Toast.LENGTH_SHORT).show()

                        if (key == roleName[0]){
                            startActivity(Intent(this, TeachersActivity::class.java))
                        }else if (key == roleName[1]){
                            startActivity(Intent(this, StudentActivity::class.java))
                        }else if (key == roleName[2]){
                            startActivity(Intent(this, AdminActivity::class.java))
                        } else {
                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                    }else{
                        Toast.makeText(this,"User does not exist",Toast.LENGTH_SHORT).show()
                    }
                } .addOnFailureListener {
                    Toast.makeText(this,"please check your connection",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                progress.dismiss()
                Toast.makeText(this,"User not found, please Sign Up ",Toast.LENGTH_SHORT).show()
            }

    }

    override fun onStart(){
        //if user exist
        fireBaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = fireBaseAuth.currentUser
        if (firebaseUser != null) {
            firebaseDatabase = FirebaseDatabase.getInstance("https://signup-attendable-default-rtdb.europe-west1.firebasedatabase.app/")
            databaseReference = firebaseDatabase.getReferenceFromUrl("https://signup-attendable-default-rtdb.europe-west1.firebasedatabase.app/")

            val ud = firebaseUser.email?.split("@")
            val uid = ud?.get(0)
            databaseReference.child("users").child(uid.toString()).get().addOnSuccessListener {
                if (it.exists()){
                    val key = it.child("role").value

                    Toast.makeText(this,"Logged in as ${it.child("firstname").value} ${it.child("lastname").value}",Toast.LENGTH_SHORT).show()


                    if (key == roleName[0]){
                        startActivity(Intent(this, TeachersActivity::class.java))
                    }else if (key == roleName[1]){
                        startActivity(Intent(this, StudentActivity::class.java))
                    }else if (key == roleName[2]){
                        startActivity(Intent(this, AdminActivity::class.java))
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }else{
                    // will re direct to login on it's own, don't add anything here.
                    // just a place holder
                }
            } .addOnFailureListener {
                // task fo failure
            }
        }
        super.onStart()
    }
}