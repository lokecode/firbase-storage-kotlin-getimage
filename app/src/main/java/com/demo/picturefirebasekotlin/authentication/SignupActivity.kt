package com.demo.picturefirebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val auth = FirebaseAuth.getInstance()



        buttonReg.setOnClickListener {
            val email = editText.text.toString()
            val password = editText2.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                Toast.makeText(this, "password", Toast.LENGTH_LONG).show()
            else if (password.length <=5){
                Toast.makeText(this,"password has t be over 5 letters", Toast.LENGTH_LONG).show()
                editText2.setText("") }
            else
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignupActivity, UploadUserDataActivity::class.java))
                    } else {
                        Toast.makeText(this, "User not created", Toast.LENGTH_SHORT).show()
                    }
                }
            editText.setText("")
            editText2.setText("")
        }
    }
}