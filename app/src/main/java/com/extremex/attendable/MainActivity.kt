package com.extremex.attendable

import android.Manifest.permission.*
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.security.Permission
import java.security.Permissions
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        val loginActivityScreen = Intent(this, LoginActivity::class.java)
        while (!isReadExternalStorageAvailable()) {
            requestPermission()
        }
        if (isReadExternalStorageAvailable()) {
            startActivity(loginActivityScreen)
        }
    }
    private fun isReadExternalStorageAvailable(): Boolean {
        return (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE),
            1001)
    }
}