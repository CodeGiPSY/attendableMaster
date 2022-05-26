package com.extremex.attendable

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class StudentActivity : AppCompatActivity() {

    private lateinit var fireBaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        val signOutButton = findViewById<Button>(R.id.signoutBtn)
        val user: TextView = this@StudentActivity.findViewById(R.id.User)
        val mkAttendance: Button = this@StudentActivity.findViewById(R.id.MarkAttendanceButton)
        val vAttendance: Button = this@StudentActivity.findViewById(R.id.ViewAttendance)
        val eventBoard: TextView = this@StudentActivity.findViewById(R.id.EventBoard)
        val loginScreen =  Intent(this,LoginActivity::class.java)


        // display User
        user.text = "User"

        // event board display
        eventBoard.text = resources.getText(R.string.no_events_string)

        mkAttendance.setOnClickListener {
            val uiInflater = LayoutInflater.from(this).inflate(R.layout.mk_attendance,null)
            val popUpBox = AlertDialog.Builder(this)
                .setView(uiInflater)
                .setCancelable(false)
            val closeButton: Button = uiInflater.findViewById(R.id.CloseButton)
            val  QRCode: ImageView = uiInflater.findViewById(R.id.QRCodeView)
            val conectCode: TextView = uiInflater.findViewById(R.id.ConnectionCode)
            val qrBuilder = QRCodeWriter()
            if (!conectCode.text.toString().isNullOrBlank()){
                try {
                    val qrCode = qrBuilder.encode(conectCode.text.toString(), BarcodeFormat.QR_CODE,512,512)
                    val bitmpConfig = Bitmap.createBitmap(qrCode.width,qrCode.height,Bitmap.Config.RGB_565)
                    for(x in 0 until qrCode.width){
                        for(y in 0 until qrCode.height){
                            bitmpConfig.setPixel(x,y,if (qrCode[x,y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    QRCode.setImageBitmap(bitmpConfig)
                } catch (e: WriterException){

                }
            }

            popUpBox.show()
            closeButton.setOnClickListener {
                popUpBox.setCancelable(true)
            }
        }

        vAttendance.setOnClickListener {
            // pass
        }


        fireBaseAuth = FirebaseAuth.getInstance()
        signOutButton.setOnClickListener {
            fireBaseAuth.signOut()
            startActivity(loginScreen);
            finish()
        }
    }
}